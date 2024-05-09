package com.project.biscuit.domain.order.service;

import com.project.biscuit.domain.goods.entity.Goods;
import com.project.biscuit.domain.goods.repository.GoodsRepository;
import com.project.biscuit.domain.images.entity.Images;
import com.project.biscuit.domain.images.repository.ImagesRepository;
import com.project.biscuit.domain.order.entity.Orders;
import com.project.biscuit.domain.order.repository.OrdersRepository;
import com.project.biscuit.domain.recycle.entity.Recycle;
import com.project.biscuit.domain.recycle.repository.RecycleRepository;
import com.project.biscuit.domain.user.entity.User;
import com.project.biscuit.domain.user.repository.UserRepository;
import com.project.biscuit.domain.order.dto.OrderRequestDto;
import com.project.biscuit.domain.order.dto.OrderResponseDto;
import com.project.biscuit.domain.order.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final RecycleRepository recycleRepository;
    private final ImagesRepository imagesRepository;

    // 주문 내역 추가 post
    public Orders addOrders(OrderRequestDto req) {
        User user = userRepository.findById(req.getUser_no()).orElseThrow(
                () -> new IllegalArgumentException("Not Exist User"));
        Orders order = req.toOrders(user);

        switch (req.getPd_type()) {
            case "G" -> {
                Goods goods = goodsRepository.findById(req.getPd_no()).orElseThrow(
                        () -> new IllegalArgumentException("Not Exist Goods"));
                order.setGoods(goods);
            }
            case "R" -> {
                Recycle recycle = recycleRepository.findByRecycleNo(req.getPd_no()).orElseThrow(
                        () -> new IllegalArgumentException("Not Exist Recycle"));
                order.setRecycle(recycle);
            }
            case "T" -> {
                Goods ticket = goodsRepository.findById(req.getPd_no()).orElseThrow(
                        () -> new IllegalArgumentException("Not Exist Goods"));
                order.setGoods(ticket);
                order.setStatus(OrderStatus.E);
            }
        }
        return ordersRepository.save(order);
    }

    // 주문 내역 취소 get
    public String cancelOrders(long no) {
        Orders order =  ordersRepository.findById(no).orElseThrow(
                () -> new IllegalArgumentException("Not Exist Orders"));
        order.setStatus(OrderStatus.C);
        ordersRepository.save(order);

        return "{result: success}";
    }

    // 주문 내역 상태 변경 post
    public String changeStatus(OrderRequestDto req) {
        if(req.getOrder_status().equals("C")) throw new IllegalArgumentException("api select error");
        Orders order = ordersRepository.findById(req.getOrder_no()).orElseThrow(
                () -> new IllegalArgumentException("Not Exist Orders"));

        order.setStatus(OrderStatus.valueOf(req.getOrder_status()));
        ordersRepository.save(order);

        return "{result: success}";
    }

    // 주문 내역 전체 확인 (모든 사용자) get
    public List<OrderResponseDto> getOrderListforAdm() {
        List<OrderResponseDto> resList = ordersRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(ord -> {
            if(ord.getGoods() != null) return OrderResponseDto.ofWithGoods(ord, null);
            else return OrderResponseDto.ofWithRecycle(ord);
        }).toList();
        return resList;
    }

    // 주문 내역 전체 확인 (특정 사용자) get
    public List<OrderResponseDto> getOrderList(long no) {
        List<Orders> ordList = ordersRepository.findByUser_noOrderByCreatedAtDesc(no);
        List<OrderResponseDto> resList = ordList.stream().map(ord -> {
            if(ord.getGoods() != null) {
                Images img = imagesRepository.findByGoods_NoAndThumbnailYn(ord.getGoods().getNo(), "Y").orElseThrow(() -> new IllegalArgumentException("Not found Image"));
                return OrderResponseDto.ofWithGoods(ord, img);
            }
            else return OrderResponseDto.ofWithRecycle(ord);
        }).toList();
        return resList;
    }

    // 주문 내역 상태별 확인 (특정 사용자) post
    public List<OrderResponseDto> getOrderListWithStatus(long no, String str) {
        List<Orders> ordList = ordersRepository.findByUser_noAndStatusOrderByCreatedAtDesc(no, OrderStatus.valueOf(str));
        List<OrderResponseDto> resList = ordList.stream().map(ord -> {
            if(ord.getGoods() != null) {
                Images img = imagesRepository.findByGoods_NoAndThumbnailYn(ord.getGoods().getNo(), "Y").orElseThrow(() -> new IllegalArgumentException("Not found Image"));
                return OrderResponseDto.ofWithGoods(ord , img);
            }
            else return OrderResponseDto.ofWithRecycle(ord);
        }).toList();
        return resList;
    }

}
