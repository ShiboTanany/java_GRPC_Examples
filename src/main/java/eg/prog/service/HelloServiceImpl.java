package eg.prog.service;

import eg.prog.grpc.HelloServiceGrpc;
import eg.prog.grpc.HelloResponse;
import eg.prog.grpc.HelloRequest;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {


	@Override
	public void getHelloStream(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		System.out.println("Request received from client:\n" + request);

		String greeting = new StringBuilder().append("Hello, ")
				.append(request.getFirstName())
				.append(" ")
				.append(request.getLastName())
				.toString();


		int counter = 0;
		while (counter < 5) {

			HelloResponse response = HelloResponse.newBuilder()
					.setGreeting(greeting + "" + counter)
					.build();
			System.out.println(response);
			responseObserver.onNext(response);
			counter++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		responseObserver.onCompleted();
	}

	@Override
	public void hello(
			HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

	}


	@Override
	public StreamObserver<HelloRequest> getHelloStream3(StreamObserver<HelloResponse> responseObserver) {

		return new StreamObserver<>() {
			@Override
			public void onNext(HelloRequest helloRequest) {
				System.out.println("Request received from client:\n" + helloRequest);

				String greeting = new StringBuilder().append("Hello, ")
						.append(helloRequest.getFirstName())
						.append(" ")
						.append(helloRequest.getLastName())
						.toString();
				int counter = 0;
				while (counter < 5) {

					HelloResponse response = HelloResponse.newBuilder()
							.setGreeting(greeting + "" + counter)
							.build();
					System.out.println(response);
					responseObserver.onNext(response);
					counter++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("error ");
			}

			@Override
			public void onCompleted() {
				System.out.println("completed ");

			}
		};
	}
}