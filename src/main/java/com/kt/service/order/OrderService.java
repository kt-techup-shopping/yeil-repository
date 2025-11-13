package com.kt.service.order;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.domain.order.Order;
import com.kt.domain.order.OrderStatus;
import com.kt.domain.order.Receiver;
import com.kt.domain.orderProduct.OrderProduct;
import com.kt.dto.order.OrderResponse;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderProduct.OrderProductRepository;
import com.kt.repository.product.ProductRepository;
import com.kt.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderProductRepository orderProductRepository;

	// 주문 생성
	public void create(
		Long userId,
		Long productId,
		String receiverName,
		String receiverAddress,
		String receiverMobile,
		Long quantity
	) {
		var product = productRepository.findByIdOrThrow(productId);
		Preconditions.validate(product.canProvide(quantity), ErrorCode.NOT_ENOUGH_STOCK);

		var user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);
		var receiver = new Receiver(receiverName, receiverAddress, receiverMobile);

		var order = orderRepository.save(Order.create(receiver, user));
		var orderProduct = orderProductRepository.save(new OrderProduct(order, product, quantity));

		product.decreaseStock(quantity);

		product.mapToOrderProduct(orderProduct);
		order.mapToOrderProduct(orderProduct);
	}

	public OrderResponse.Detail detail(Long id) {
		// fetch join 적용
		var order = orderRepository.findOrderDetail(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
		var orderProduct = order.getOrderProducts().getFirst();
		var product = orderProduct.getProduct();

		return new OrderResponse.Detail(
			order.getId(),
			order.getReceiver().getName(),
			order.getReceiver().getAddress(),
			order.getReceiver().getMobile(),
			product.getName(),
			orderProduct.getQuantity(),
			product.getPrice() * (orderProduct.getQuantity()),
			order.getStatus(),
			order.getDeliveredAt(),
			order.getCreatedAt()
		);
	}
}
