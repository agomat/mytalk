package info.mytalk.core;

public class ARI {
	
	// ARI's part Auth
	public static class Auth {
		public String username;
		public String password;
	}
	
	public static class Connection {
        public String myIp;
        public String speakerIp;
        public String sdpCall;
	      public String candidate;
        public Connection(String _myIp, String _speakerIp, String _sdpCall, String _candidate) {
            myIp = _myIp;
            speakerIp = _speakerIp;
            sdpCall = _sdpCall;
	          candidate = _candidate;
        }
	}
	
	public String auth;
	public String req;
	public String info;
	
	public ARI(String a, String r, String i) {
	    this.auth = a;
		this.req = r;
		this.info = i;
	}
	public String toString() {
		String out = "";
		out = out.concat(" PACCHETTO ARI -------------------------------------------\n");
		out = out.concat(" auth: "+auth+" \n");
		out = out.concat(" req: "+req+" \n");
		out = out.concat(" info: "+info+" \n");
		out = out.concat(" ---------------------------------------------------------");
		return out;
	}
	
	public ARI(){
		this(null,null,null);
	}
}
