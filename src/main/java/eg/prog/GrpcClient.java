package eg.prog;

import eg.prog.grpc.HelloRequest;
import eg.prog.grpc.HelloResponse;
import eg.prog.grpc.HelloServiceGrpc;
import eg.prog.grpc.HelloServiceGrpc.HelloServiceStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class GrpcClient {
	public static void main(String[] args) throws InterruptedException {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().maxInboundMessageSize(50 * 1024 * 1024).build();
		StreamObserver streamObserver = new StreamObserver<HelloResponse>() {
			@Override
			public void onNext(HelloResponse helloResponse) {
				System.out.println(helloResponse.getGreeting());
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("error " + throwable);
			}

			@Override
			public void onCompleted() {
				System.out.println("completed");
			}
		};
		HelloServiceStub stub = HelloServiceGrpc.newStub(channel);
		stub.getHelloStream(eg.prog.grpc.HelloRequest.newBuilder().setFirstName("de").setLastName("shibo").build(), streamObserver);

		System.out.println("$$$$$$$$$$$$$"
		);
		StreamObserver request = stub.getHelloStream3(streamObserver);
		int counter = 5;
		while (counter > 0) {
			counter--;
			request.onNext(HelloRequest.newBuilder().setFirstName("counter " + counter).build());
		}


		channel.awaitTermination(10, TimeUnit.MINUTES);
	}
}
