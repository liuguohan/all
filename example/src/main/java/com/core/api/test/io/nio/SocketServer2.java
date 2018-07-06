package com.core.api.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SocketServer2 {

	/**
     * 改进的java nio server的代码中，由于buffer的大小设置的比较小。
     * 我们不再把一个client通过socket channel多次传给服务器的信息保存在beff中了（因为根本存不下）<br>
     * 我们使用socketchanel的hashcode作为key（当然您也可以自己确定一个id），信息的stringbuffer作为value，存储到服务器端的一个内存区域MESSAGEHASHCONTEXT。
     * 
     * 如果您不清楚ConcurrentHashMap的作用和工作原理，请自行百度/Google
     */
    private static final ConcurrentMap<Integer, StringBuffer> MESSAGEHASHCONTEXT = new ConcurrentHashMap<Integer , StringBuffer>();

	
	public static void main(String[] args) throws IOException {
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.setReuseAddress(true);
		serverSocket.bind(new InetSocketAddress(83));
		
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		try {
			while(true){
				//如果条件成立，说明本次询问selector，并没有获取到任何准备好的、感兴趣的事件
                //java程序对多路复用IO的支持也包括了阻塞模式 和非阻塞模式两种。
				if( selector.select(100) == 0 ) {
					//================================================
                    //      这里视业务情况，可以做一些然并卵的事情
                    //================================================
                    continue;
				}
				
				//这里就是本次询问操作系统，所获取到的“所关心的事件”的事件类型（每一个通道都是独立的）
				Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
				while( selectedKeys.hasNext() ) {
					SelectionKey readyKey = selectedKeys.next();
					//这个已经处理的readyKey一定要移除。如果不移除，就会一直存在在selector.selectedKeys集合中
		            //待到下一次selector.select() > 0时，这个readyKey又会被处理一次
					selectedKeys.remove();
					
					
					SelectableChannel selectableChannel = readyKey.channel();
					
					if( readyKey.isValid() && readyKey.isAcceptable() ) {
						System.out.println("======channel通道已经准备好=======");
						/**
		                 * 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
		                 * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
		                 * 否则无法监听到这个socket channel到达的数据
		                 * */
						ServerSocketChannel serverSocketChannel_ = (ServerSocketChannel)selectableChannel;
						SocketChannel socketChannel = serverSocketChannel_.accept();
						registerSocketChannel(socketChannel, selector);
					}else if( readyKey.isValid() && readyKey.isConnectable() ) {
						System.out.println("======socket channel 建立连接=======");
					}else if( readyKey.isValid() && readyKey.isReadable() ) {
						System.out.println("======socket channel 数据准备完成，可以去读==读取=======");
						readSocketChannel(readyKey);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			serverSocket.close();
		}
		
		
	}
	
	/**
	 * 
	 * 在server socket channel接收到/准备好 一个新的 TCP连接后。
     * 就会向程序返回一个新的socketChannel。<br>
     * 但是这个新的socket channel并没有在selector“选择器/代理器”中注册，
     * 所以程序还没法通过selector通知这个socket channel的事件。
     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个
     * socket channel感兴趣的事件
	 * @param socketChannel
	 * @param selector
	 * @throws Exception
	 */
	private static void registerSocketChannel(SocketChannel socketChannel , Selector selector) throws Exception {
		
		socketChannel.configureBlocking(false);
		//socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
		socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(50));
	}
	
	/**
     * 这个方法用于读取从客户端传来的信息。
     * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输。
     * 如果传输完成，则返回一个true的标记。
     * @param socketChannel
     * @throws Exception
     */
    private static void readSocketChannel(SelectionKey readyKey) throws Exception {
    	
    	SocketChannel clientSocketChannel = (SocketChannel)readyKey.channel();
    	//获取客户端使用的端口
    	InetSocketAddress sourceSocketAddress = (InetSocketAddress)clientSocketChannel.getRemoteAddress();
    	Integer resoucePort = sourceSocketAddress.getPort();
    	
    	//拿到这个socket channel使用的缓存区，准备读取数据
        //在后文，将详细讲解缓存区的用法概念，实际上重要的就是三个元素capacity,position和limit。
    	ByteBuffer contextBytes = (ByteBuffer)readyKey.attachment();
    	//将通道的数据写入到缓存区，注意是写入到缓存区。
        //这次，为了演示buff的使用方式，我们故意缩小了buff的容量大小到50byte，
        //以便演示channel对buff的多次读写操作
    	int realLen = 0;
    	
    	StringBuffer message = new StringBuffer();
    	
    	while( (realLen = clientSocketChannel.read(contextBytes)) != 0 ) {
    		
    		contextBytes.flip();
    		
    		int position = contextBytes.position();
    		int capacity = contextBytes.capacity();
    		byte[] messageByte = new byte[capacity];
    		contextBytes.get(messageByte, position, realLen);
    		
    		String messageEncode = new String(messageByte, 0 , realLen, "UTF-8");
    		message.append(messageEncode);
    		
    		contextBytes.clear();
    	}
    	
    	//如果发现本次接收的信息中有over关键字，说明信息接收完了
        if(URLDecoder.decode(message.toString(), "UTF-8").indexOf("over") != -1) {
            //则从messageHashContext中，取出之前已经收到的信息，组合成完整的信息
            Integer channelUUID = clientSocketChannel.hashCode();
            System.out.println("端口:" + resoucePort + "客户端发来的信息======message : " + message);
            StringBuffer completeMessage;
            //清空MESSAGEHASHCONTEXT中的历史记录
            StringBuffer historyMessage = MESSAGEHASHCONTEXT.remove(channelUUID);
            if(historyMessage == null) {
                completeMessage = message;
            } else {
                completeMessage = historyMessage.append(message);
            }
            System.out.println("端口:" + resoucePort + "客户端发来的完整信息======completeMessage : " + URLDecoder.decode(completeMessage.toString(), "UTF-8"));

            //======================================================
            //          当然接受完成后，可以在这里正式处理业务了        
            //======================================================

            //回发数据，并关闭channel
            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果", "UTF-8").getBytes());
            clientSocketChannel.write(sendBuffer);
            clientSocketChannel.close();
        } else {
            //如果没有发现有“over”关键字，说明还没有接受完，则将本次接受到的信息存入messageHashContext
        	System.out.println("端口:" + resoucePort + "客户端信息还未接受完，继续接受======message : " + URLDecoder.decode(message.toString(), "UTF-8"));
            //每一个channel对象都是独立的，所以可以使用对象的hash值，作为唯一标示
            Integer channelUUID = clientSocketChannel.hashCode();

            //然后获取这个channel下以前已经达到的message信息
            StringBuffer historyMessage = MESSAGEHASHCONTEXT.get(channelUUID);
            if(historyMessage == null) {
                historyMessage = new StringBuffer();
                MESSAGEHASHCONTEXT.put(channelUUID, historyMessage.append(message));
            }
        }
    	
    }
	
}
