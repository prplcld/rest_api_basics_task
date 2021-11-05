package com.epam.esm.restapibasics.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

//    @InjectMocks
//    private GiftCertificateServiceImpl giftCertificateService;
//
//    @Mock
//    private GiftCertificateDao giftCertificateDao;
//
//    @Mock
//    private TagDao tagDao;
//
//    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now(UTC);
//
//    @BeforeAll
//    static void setUp() {
//        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
//    }
//
//
//    @Test
//    void getAllCertificates() {
//        Long id = 1L;
//        when(giftCertificateDao.find(null, null, null, null, null)).thenReturn(provideCertificates());
//        when(tagDao.getByCertificateId(id)).thenReturn(provideTagsInCertificate().get(1L));
//
//        giftCertificateService.getAll(new SearchParamsModelDto());
//        verify(giftCertificateDao).find(null, null, null, null, null);
//        verify(tagDao).getByCertificateId(id);
//    }
//
//    @Test
//    void getCertificateById() {
//        Long id = 1L;
//        when(giftCertificateDao.getById(id)).thenReturn(provideCertificates().get(id.intValue()));
//        when(tagDao.getByCertificateId(id+1)).thenReturn(provideTagsInCertificate().get(2L));
//
//        GiftCertificateDto expected = GiftCertificateDto.createFromCertificate(provideCertificates().get(1), provideTagsInCertificate().get(2L));
//        GiftCertificateDto actual = giftCertificateService.getById(1L);
//
//        assertEquals(expected, actual);
//    }
//
//
//    @Test
//    void deleteCertificate() {
//        Long id = 1L;
//        when(giftCertificateDao.delete(id)).thenReturn(true);
//        when(giftCertificateDao.deleteFromTags(id)).thenReturn(true);
//
//        giftCertificateService.delete(id);
//        verify(giftCertificateDao).delete(id);
//        verify(giftCertificateDao).deleteFromTags(id);
//    }
//
//    @Test
//    void updateGiftCertificate() throws NoTagFoundException {
////        GiftCertificateDto giftCertificate = provideGiftCertificateDtos().get(1);
////        TagDto tag = giftCertificate.getTags().get(0);
////        TagDto tag1 = giftCertificate.getTags().get(1);
////
////        when(giftCertificateDao.update(giftCertificate.toCertificate())).thenReturn(true);
////        when(tagDao.getByName(tag.getName())).thenReturn(tag.toTag());
////        when(tagDao.getByName(tag1.getName())).thenReturn(tag1.toTag());
////        when(giftCertificateDao.attachTag(giftCertificate.getId(), tag.getId())).thenReturn(true);
////        when(giftCertificateDao.attachTag(giftCertificate.getId(), tag1.getId())).thenReturn(true);
////        giftCertificateService.update(giftCertificate);
////
////        verify(giftCertificateDao).update(giftCertificate.toCertificate());
//    }
//
//    List<GiftCertificateDto> provideGiftCertificateDtos() {
//        List<GiftCertificateDto> giftCertificateDtos = new ArrayList<>();
//
//        for (GiftCertificate g : provideCertificates()) {
//            giftCertificateDtos.add(GiftCertificateDto.createFromCertificate(g, provideTagsInCertificate().get(g.getId())));
//        }
//
//        return giftCertificateDtos;
//    }
//
//
//    Map<Long, List<Tag>> provideTagsInCertificate() {
//        Map<Long, List<Tag>> tagsInCertificate = new HashMap<>();
//        Tag tag = new Tag();
//        tag.setId(1L);
//        tag.setName("1");
//
//        List<Tag> tags  = new ArrayList<>();
//        tags.add(tag);
//
//        tagsInCertificate.put(1L, tags);
//
//        tags = new ArrayList<>();
//        tags.add(tag);
//        tag = new Tag();
//        tag.setId(2L);
//        tag.setName("2");
//        tags.add(tag);
//
//        tagsInCertificate.put(2L, tags);
//
//        tagsInCertificate.put(3L, new ArrayList<>());
//
//        return tagsInCertificate;
//    }
//
//    List<GiftCertificate> provideCertificates() {
//        List<GiftCertificate> giftCertificates = new ArrayList<>();
//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setId(1L);
//        giftCertificate.setName("1");
//        giftCertificate.setDescription("1");
//        giftCertificate.setPrice(new BigDecimal(1));
//        giftCertificate.setDuration(1);
//        giftCertificate.setCreateDate(LOCAL_DATE_TIME);
//        giftCertificate.setLastUpdateDate(LOCAL_DATE_TIME);
//        giftCertificates.add(giftCertificate);
//
//        giftCertificate = new GiftCertificate();
//        giftCertificate.setId(2L);
//        giftCertificate.setName("2");
//        giftCertificate.setDescription("2");
//        giftCertificate.setPrice(new BigDecimal(2));
//        giftCertificate.setDuration(2);
//        giftCertificate.setCreateDate(LOCAL_DATE_TIME);
//        giftCertificate.setLastUpdateDate(LOCAL_DATE_TIME);
//        giftCertificates.add(giftCertificate);
//
//        giftCertificate = new GiftCertificate();
//        giftCertificate.setId(3L);
//        giftCertificate.setName("3");
//        giftCertificate.setDescription("3");
//        giftCertificate.setPrice(new BigDecimal(3));
//        giftCertificate.setDuration(3);
//        giftCertificate.setCreateDate(LOCAL_DATE_TIME);
//        giftCertificate.setLastUpdateDate(LOCAL_DATE_TIME);
//        giftCertificates.add(giftCertificate);
//        return giftCertificates;
//    }
}