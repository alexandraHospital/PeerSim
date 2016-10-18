package tme1;

import java.util.HashSet;
import java.util.Set;

import peersim.config.Configuration;
import peersim.core.Network;
import peersim.core.Node;
import peersim.edsim.EDProtocol;
import peersim.transport.Transport;

public class FifoBroadcast implements EDProtocol, BroadcastProtocol {
	
	private static final String PAR_TRANSPORT = "transport";
	
	
	private final int transport_id;
	private final int protocol_id;
	
	private Set<Message> rec;
	private Set<Message> pending;
	
	private int next[];
	
	public FifoBroadcast(String prefix) {
		rec = new HashSet<Message>();
		pending = new HashSet<Message>();
		next = new int[Network.size()];
		for(int i = 0 ; i< Network.size();i++) { 
			next[i] = 1;
		}

		transport_id = Configuration.getPid(prefix+"."+PAR_TRANSPORT);
		String tmp[]=prefix.split("\\.");
		protocol_id=Configuration.lookupPid(tmp[tmp.length-1]);
	}

	@Override
	public void broadcast(Node src, Message m) {
		
		
	}

	@Override
	public void deliver(Node host, Message m) {
		int pid_dessus=((Message)m.getContent()).getPid();
		((EDProtocol)host.getProtocol(pid_dessus)).processEvent(host, pid_dessus, m.getContent()); 
		
	}

	@Override
	public void processEvent(Node node, int pid, Object event) {
		if(protocol_id != pid){
			throw new RuntimeException("Receive Message for wrong protocol");
		}
		if(event instanceof Message ){
			Message m = (Message)event;
			Node s = node;
			pending.add(m);
			
			while(pending.contains()) {
				
			}
			
			deliver(node,m);
		}
		
	}
	
	@Override
	public Object clone(){
		FifoBroadcast bb = null;
		try { bb = (FifoBroadcast) super.clone();}
		catch( CloneNotSupportedException e ) {} // never happens
		return bb;
	}

}
