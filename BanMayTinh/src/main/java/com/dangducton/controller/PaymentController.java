package com.dangducton.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.PaypalPaymentIntent;
import com.dangducton.PaypalPaymentMethod;
import com.dangducton.dto.GioHangDTO;
import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Chitietdonhang;
import com.dangducton.entities.Donhang;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Thanhtoan;
import com.dangducton.entities.Vanchuyen;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.PaypalService;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.service.ThanhToanServiceImpl;
import com.dangducton.service.VanChuyenServiceImpl;
import com.dangducton.util.Paypal;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {
	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	ThanhToanServiceImpl thanhToanServiceImpl;
	@Autowired
	VanChuyenServiceImpl vanChuyenServiceImpl;
	@Autowired
	DonHangServiceImpl donHangServiceImpl;
	public static final String URL_PAYPAL_SUCCESS = "banhang/success";
	public static final String URL_PAYPAL_CANCEL = "banhang/cancel";

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaypalService paypalService;

	private String hoten1;
	private String email1;
	private String diachi1;
	private String dienthoai1;
	private String hotenkhac1;
	private String diachikhac1;
	private boolean checkbox1;
	private String dienthoaikhac1;
	private String ghichu1;
	private Integer vanchuyen1;

	@PostMapping("/banhang/thanhtoanonline")
	public String pay(HttpServletRequest request, @RequestParam(name = "hoten") String hoten,
			@RequestParam(name = "email") String email, @RequestParam(name = "diachi") String diachi,
			@RequestParam(name = "dienthoai") String dienthoai,
			@RequestParam(name = "diachikhacid", required = true, defaultValue = "false") boolean checkbox,
			@RequestParam(name = "hotenkhac") String hotenkhac, @RequestParam(name = "diachikhac") String diachikhac,
			@RequestParam(name = "dienthoaikhac") String dienthoaikhac, @RequestParam(name = "ghichu") String ghichu,
			@RequestParam(name = "vanchuyen", required = true, defaultValue = "0") Integer vanchuyen,
			RedirectAttributes ra) {
		String cancelUrl = Paypal.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
		String successUrl = Paypal.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
		if (checkbox) {
			if (hotenkhac.length() > 10 && hoten.length() < 50) {
				if (vanchuyen != 0) {
					if (diachikhac.length() > 20 && diachikhac.length() < 50) {
						if (dienthoaikhac.length() > 9 && dienthoaikhac.length() < 11
								&& dienthoaikhac.charAt(0) == '0') {
							hoten1 = hoten;
							email1 = email;
							diachi1 = diachi;
							dienthoai1 = dienthoai;
							hotenkhac1 = hotenkhac;
							diachikhac1 = diachikhac;
							checkbox1 = checkbox;
							dienthoaikhac1 = dienthoaikhac;
							ghichu1 = ghichu;
							vanchuyen1 = vanchuyen;

							try {
								Principal principal = request.getUserPrincipal();
								Nguoidung ng = new Nguoidung();
								ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
								double tongSoTien = 0;
								List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
								for (Chitietdonhang ct : list) {
									tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
								}
								double usd = tongSoTien / 23200;
								Payment payment = paypalService.createPayment(usd, "USD", PaypalPaymentMethod.paypal,
										PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
								for (Links links : payment.getLinks()) {
									if (links.getRel().equals("approval_url")) {
										return "redirect:" + links.getHref();
									}
								}
							} catch (PayPalRESTException e) {
								log.error(e.getMessage());
							}
						} else {
							ra.addFlashAttribute("status",
									"Yêu cầu nhập đúng định dạng điện thoại người nhận gồm 10 số");
							return "redirect:/banhang/thanhtoan";
						}
					} else {
						ra.addFlashAttribute("status", "Yêu cầu nhập đúng địa chỉ giao hàng");
						return "redirect:/banhang/thanhtoan";
					}
				} else {
					ra.addFlashAttribute("status", "Yêu cầu chọn phương thức vận chuyển");
					return "redirect:/banhang/thanhtoan";
				}
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ họ tên người nhận");
				return "redirect:/banhang/thanhtoan";
			}
		}
		if (checkbox == false) {
			if (hoten.length() > 10 && hoten.length() < 50) {
				if (vanchuyen != 0) {
					if (diachi.length() > 20 && diachi.length() < 50) {
						if (dienthoai.length() > 9 && dienthoai.length() < 11 && dienthoai.charAt(0) == '0') {
							hoten1 = hoten;
							email1 = email;
							diachi1 = diachi;
							dienthoai1 = dienthoai;
							checkbox1 = checkbox;
							ghichu1 = ghichu;
							vanchuyen1 = vanchuyen;
							try {
								Principal principal = request.getUserPrincipal();
								Nguoidung ng = new Nguoidung();
								ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
								double tongSoTien = 0;
								List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
								for (Chitietdonhang ct : list) {
									tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());

								}
								double usd = tongSoTien / 23200;
								Payment payment = paypalService.createPayment(usd, "USD", PaypalPaymentMethod.paypal,
										PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
								for (Links links : payment.getLinks()) {
									if (links.getRel().equals("approval_url")) {
										return "redirect:" + links.getHref();
									}
								}
							} catch (PayPalRESTException e) {
								log.error(e.getMessage());
							}
						} else {
							ra.addFlashAttribute("status", "Yêu cầu nhập đúng định dạng điện thoại gồm 10 số");
							return "redirect:/banhang/thanhtoan";
						}
					} else {
						ra.addFlashAttribute("status", "Yêu cầu nhập đúng địa chỉ giao hàng");
						return "redirect:/banhang/thanhtoan";
					}
				} else {
					ra.addFlashAttribute("status", "Yêu cầu chọn phương thức vận chuyển");
					return "redirect:/banhang/thanhtoan";
				}
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ họ tên");
				return "redirect:/banhang/thanhtoan";
			}
		}
		return "redirect:/banhang/thanhtoan";
	}

	@GetMapping(URL_PAYPAL_CANCEL)
	public String cancelPay(Model model, @PageableDefault(size = 12) Pageable pageable, HttpServletRequest request) {
		model.addAttribute("module", "Shop");
		model.addAttribute("gioHang", "Giỏ hàng");
		model.addAttribute("status", "Mua hàng không thành công");
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("tenNguoiDung", ng);
		List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
		List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
		double tongSoTien = 0;
		for (Chitietdonhang ct : list) {
			tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
			Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
			listGioHang.add(new GioHangDTO(ct.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
					ct.getSoluong(), ct.getIdSanpham().getId(), ct.getId(), asp.getIdSanpham().getId(),asp.getIdSanpham().getIdChitiet()));
		}

		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("listGioHang", listGioHang);
		return "frontend/giohang";
	}

	@GetMapping(URL_PAYPAL_SUCCESS)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			Model model, @PageableDefault(size = 12) Pageable pageable, HttpServletRequest request,
			RedirectAttributes ra) {
		if (checkbox1) {
			if (hotenkhac1.length() > 10 && hoten1.length() < 50) {
				if (vanchuyen1 != 0) {
					if (diachikhac1.length() > 20 && diachikhac1.length() < 50) {
						if (dienthoaikhac1.length() > 9 && dienthoaikhac1.length() < 11
								&& dienthoaikhac1.charAt(0) == '0') {
							Principal principal = request.getUserPrincipal();
							Nguoidung ng = new Nguoidung();
							ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
							model.addAttribute("tenNguoiDung", ng);
							Date date = new Date();
							Donhang dh = new Donhang();
							Thanhtoan tt = new Thanhtoan();
							tt = thanhToanServiceImpl.findById(1).get();
							Vanchuyen vc = new Vanchuyen();
							vc = vanChuyenServiceImpl.findById(vanchuyen1).get();
							List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
							List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
							double tongSoTien = 0;
							for (Chitietdonhang ct : list) {
								tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
							}
							ng.setHoten(hoten1);
							ng.setDiachi(diachi1);
							ng.setDienthoai(dienthoai1);
							nguoiDungServiceImpl.update(ng);
							dh.setDiachi(diachikhac1);
							dh.setDienthoai(dienthoaikhac1);
							dh.setEmail(email1);
							dh.setGhichu(ghichu1);
							int max = (donHangServiceImpl.findMaxID());
							String NhanVienID = "DHMT" + (String.format("%04d", max + 1));
							dh.setIdChitietdonhang(NhanVienID);
							dh.setIdNguoidung(ng);
							dh.setNgaytao(date);
							dh.setStatus(1);
							dh.setTennguoinhan(hotenkhac1);
							dh.setThanhtoan(tt);
							dh.setTongsotien(tongSoTien);
							dh.setTrangthaidonhang(0);
							dh.setTrangthaithanhtoan(true);
							dh.setVanchuyen(vc);
							donHangServiceImpl.save(dh);
							for (Chitietdonhang ct : list) {
								Chitietdonhang ctdh = new Chitietdonhang();
								ctdh = chiTietDonHangServiceImpl.findById(ct.getId()).get();
								ctdh.setIdDonhang(dh);
								chiTietDonHangServiceImpl.save(ctdh);
							}
							List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
							List<GioHangDTO> listGioHang1 = new ArrayList<GioHangDTO>();
							double tongSoTien1 = 0;
							for (Chitietdonhang ct : list1) {
								tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
								Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
								listGioHang.add(new GioHangDTO(ct.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
										ct.getSoluong(), ct.getIdSanpham().getId(), ct.getId(), asp.getIdSanpham().getId(),asp.getIdSanpham().getIdChitiet()));
							}
							model.addAttribute("module", "Shop");
							model.addAttribute("gioHang", "Giỏ hàng");
							model.addAttribute("status", "Mua hàng thành công");
							model.addAttribute("tenNguoiDung", ng);
							model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
							model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
							model.addAttribute("tongSoTien", tongSoTien1);
							model.addAttribute("listGioHang", listGioHang1);
							return "frontend/giohang";
						} else {
							ra.addFlashAttribute("status",
									"Yêu cầu nhập đúng định dạng điện thoại người nhận gồm 10 số");
							return "redirect:/banhang/thanhtoan";
						}
					} else {
						ra.addFlashAttribute("status", "Yêu cầu nhập đúng địa chỉ giao hàng");
						return "redirect:/banhang/thanhtoan";
					}
				} else {
					ra.addFlashAttribute("status", "Yêu cầu chọn phương thức vận chuyển");
					return "redirect:/banhang/thanhtoan";
				}
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ họ tên người nhận");
				return "redirect:/banhang/thanhtoan";
			}
		}
		if (checkbox1 == false) {
			if (hoten1.length() > 10 && hoten1.length() < 50) {
				if (vanchuyen1 != 0) {
					if (diachi1.length() > 20 && diachi1.length() < 50) {
						if (dienthoai1.length() > 9 && dienthoai1.length() < 11 && dienthoai1.charAt(0) == '0') {
							Principal principal = request.getUserPrincipal();
							Nguoidung ng = new Nguoidung();
							ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
							model.addAttribute("tenNguoiDung", ng);
							Date date = new Date();
							Donhang dh = new Donhang();
							Thanhtoan tt = new Thanhtoan();
							tt = thanhToanServiceImpl.findById(1).get();
							Vanchuyen vc = new Vanchuyen();
							vc = vanChuyenServiceImpl.findById(vanchuyen1).get();
							ng.setHoten(hoten1);
							ng.setDiachi(diachi1);
							ng.setDienthoai(dienthoai1);
							nguoiDungServiceImpl.update(ng);
							List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
							List<GioHangDTO> listGioHang = new ArrayList<GioHangDTO>();
							double tongSoTien = 0;
							for (Chitietdonhang ct : list) {
								tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
							}

							dh.setDiachi(diachi1);
							dh.setDienthoai(dienthoai1);
							dh.setEmail(email1);
							dh.setGhichu(ghichu1);
							int max = (donHangServiceImpl.findMaxID());
							String NhanVienID = "DHMT" + (String.format("%04d", max + 1));
							dh.setIdChitietdonhang(NhanVienID);
							dh.setIdNguoidung(ng);
							dh.setNgaytao(date);
							dh.setStatus(1);
							dh.setTennguoinhan(hoten1);
							dh.setThanhtoan(tt);
							dh.setTongsotien(tongSoTien);
							dh.setTrangthaidonhang(0);
							dh.setTrangthaithanhtoan(true);
							dh.setVanchuyen(vc);
							donHangServiceImpl.save(dh);

							for (Chitietdonhang ct : list) {
								Chitietdonhang ctdh = new Chitietdonhang();
								ctdh = chiTietDonHangServiceImpl.findById(ct.getId()).get();
								ctdh.setIdDonhang(dh);
								chiTietDonHangServiceImpl.save(ctdh);
							}
							List<Chitietdonhang> list1 = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
							List<GioHangDTO> listGioHang1 = new ArrayList<GioHangDTO>();
							double tongSoTien1 = 0;
							for (Chitietdonhang ct : list1) {
								tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());
								Anhsanpham asp = anhSanPhamServiceImpl.findAllByAnhSanPhamTheoSanPham(ct.getIdSanpham().getId());
								listGioHang.add(new GioHangDTO(ct.getIdSanpham().getTen(), asp.getUrlAnh(), ct.getGiasanpham(),
										ct.getSoluong(), ct.getIdSanpham().getId(), ct.getId(), asp.getIdSanpham().getId(),asp.getIdSanpham().getIdChitiet()));
							}
							model.addAttribute("module", "Shop");
							model.addAttribute("gioHang", "Giỏ hàng");
							model.addAttribute("status", "Mua hàng thành công");
							model.addAttribute("tenNguoiDung", ng);
							model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
							model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
							model.addAttribute("tongSoTien", tongSoTien1);
							model.addAttribute("listGioHang", listGioHang1);
							
							return "frontend/giohang";
							
						} else {
							ra.addFlashAttribute("status", "Yêu cầu nhập đúng định dạng điện thoại gồm 10 số");
							return "redirect:/banhang/thanhtoan";
						}
					} else {
						ra.addFlashAttribute("status", "Yêu cầu nhập đúng địa chỉ giao hàng");
						return "redirect:/banhang/thanhtoan";
					}
				} else {
					ra.addFlashAttribute("status", "Yêu cầu chọn phương thức vận chuyển");
					return "redirect:/banhang/thanhtoan";
				}
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ họ tên");
				return "redirect:/banhang/thanhtoan";
			}
		}
		return "redirect:/banhang/thanhtoan";
	}
}