package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.OrderDao;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Order;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.OrderService;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import com.epam.esm.restapibasics.service.dto.util.DtoMappingUtil;
import com.epam.esm.restapibasics.service.exception.EmptyOrderException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.ZoneOffset.UTC;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<OrderDto> findAll(Paginator paginator) {
        return orderDao.findAll(paginator)
                .stream()
                .map(DtoMappingUtil::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findByUser(Long userId, Paginator paginator) {
        return orderDao.findByUser(paginator, userId)
                .stream()
                .map(DtoMappingUtil::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = orderDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return DtoMappingUtil.mapToOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        long userId = orderDto.getUserId();
        List<Long> certificateIds = orderDto.getCertificateIds();

        if (certificateIds.isEmpty()) {
           throw new EmptyOrderException();
        }

        User user = userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        List<GiftCertificate> certificates = certificateIds.stream()
                .map(id -> giftCertificateDao.getById(id)
                        .orElseThrow(() -> new EntityNotFoundException(id)))
                .collect(Collectors.toList());



        LocalDateTime purchaseDate = LocalDateTime.now(UTC);
        BigDecimal cost = certificates.stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setPurchaseDate(purchaseDate);
        order.setCost(cost);
        order.setUser(user);
        order.setCertificates(certificates);

        Order createdOrder = orderDao.create(order);

        return DtoMappingUtil.mapToOrderDto(createdOrder);
    }

}
