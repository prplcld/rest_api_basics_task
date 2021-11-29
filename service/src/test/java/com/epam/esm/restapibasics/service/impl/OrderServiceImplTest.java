package com.epam.esm.restapibasics.service.impl;

import com.epam.esm.restapibasics.model.dao.GiftCertificateDao;
import com.epam.esm.restapibasics.model.dao.OrderDao;
import com.epam.esm.restapibasics.model.dao.Paginator;
import com.epam.esm.restapibasics.model.dao.UserDao;
import com.epam.esm.restapibasics.model.entity.GiftCertificate;
import com.epam.esm.restapibasics.model.entity.Order;
import com.epam.esm.restapibasics.model.entity.User;
import com.epam.esm.restapibasics.service.dto.OrderDto;
import com.epam.esm.restapibasics.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private UserDao userDao;

    @Mock
    private GiftCertificateDao giftCertificateDao;


    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(OrderServiceImplTest.class);
    }

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Test
    void testFindAll() {
        Paginator paginator = new Paginator(null, null);
        when(orderDao.findAll(paginator)).thenReturn(provideOrders());

        List<OrderDto> expectedDtoList = provideOrderDtoList();
        List<OrderDto> actualDtoList = orderService.findAll(paginator);

        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindByUser() {
        Paginator pageContext = new Paginator(null, null);
        long userId = 1;
        when(orderDao.findByUser(pageContext, userId)).thenReturn(provideOrders());

        List<OrderDto> expectedDtoList = provideOrderDtoList();
        List<OrderDto> actualDtoList = orderService.findByUser(userId, pageContext);

        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    void testFindById() {
        Order order = provideOrders().get(0);
        when(orderDao.findById(order.getId())).thenReturn(Optional.of(order));

        OrderDto expectedDto = provideOrderDtoList().get(0);
        OrderDto actualDto = orderService.findById(order.getId());

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testFindByIdWhenOrderNotFound() {
        long orderId = 1;
        when(orderDao.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.findById(orderId));
    }

    @Test
    void testCreateOrder() {
        User user = provideUser();
        GiftCertificate certificate = provideCertificates().get(0);
        OrderDto orderDto = provideOrderDtoList().get(0);
        Order order = provideOrders().get(0);

        when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        when(giftCertificateDao.getById(anyLong())).thenReturn(Optional.of(certificate));
        when(orderDao.create(any(Order.class))).thenReturn(order);

        orderService.createOrder(orderDto);

        verify(orderDao).create(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertTrue(capturedOrder.getCost() != null && capturedOrder.getPurchaseDate() != null);
    }

    private List<Order> provideOrders() {
        User user = provideUser();
        List<GiftCertificate> certificates = provideCertificates();

        Order firstOrder = new Order();
        firstOrder.setId(1L);
        firstOrder.setUser(user);
        firstOrder.setCertificates(certificates);

        Order secondOder = new Order();
        secondOder.setId(2L);
        secondOder.setUser(user);
        secondOder.setCertificates(certificates);

        Order thirdOrder = new Order();
        thirdOrder.setId(3L);
        thirdOrder.setUser(user);
        thirdOrder.setCertificates(certificates);

        return List.of(firstOrder, secondOder, thirdOrder);
    }

    private List<OrderDto> provideOrderDtoList() {
        User user = provideUser();
        List<Long> certificateIds = provideCertificates()
                .stream()
                .map(GiftCertificate::getId)
                .collect(Collectors.toList());

        OrderDto firstDto = new OrderDto();
        firstDto.setId(1L);
        firstDto.setUserId(user.getId());
        firstDto.setCertificateIds(certificateIds);

        OrderDto secondDto = new OrderDto();
        secondDto.setId(2L);
        secondDto.setUserId(user.getId());
        secondDto.setCertificateIds(certificateIds);

        OrderDto thirdDto = new OrderDto();
        thirdDto.setId(3L);
        thirdDto.setUserId(user.getId());
        thirdDto.setCertificateIds(certificateIds);

        return List.of(firstDto, secondDto, thirdDto);
    }

    private User provideUser() {
        User user = new User();
        user.setId(1L);

        return user;
    }

    private List<GiftCertificate> provideCertificates() {
        GiftCertificate firstCertificate = new GiftCertificate();
        firstCertificate.setId(1L);
        firstCertificate.setPrice(BigDecimal.TEN);

        GiftCertificate secondCertificate = new GiftCertificate();
        secondCertificate.setId(2L);
        secondCertificate.setPrice(BigDecimal.TEN);

        GiftCertificate thirdCertificate = new GiftCertificate();
        thirdCertificate.setId(3L);
        thirdCertificate.setPrice(BigDecimal.TEN);

        return List.of(firstCertificate, secondCertificate, thirdCertificate);
    }
}
