package eg.prog;

import eg.prog.service.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
	public static void main(String[] args) throws Exception {
		Server server = ServerBuilder.forPort(8080)
				.addService(new HelloServiceImpl())
				.maxInboundMessageSize(50 * 1024 * 1024)
				.build();

		server.start();
		server.awaitTermination();
	}
}