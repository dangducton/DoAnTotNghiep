package com.dangducton.controller;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.service.ThanhToanServiceImpl;
import com.dangducton.service.VanChuyenServiceImpl;

@Controller
public class CheckOutController {
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
	@Autowired
	JavaMailSender javaMailSender;

	@GetMapping(value = { "/banhang/thanhtoan" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status, HttpServletRequest request) {
		model.addAttribute("title", "Thanh toán");
		model.addAttribute("status", status);
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
					ct.getSoluong(), ct.getIdSanpham().getId(), ct.getId(), asp.getIdSanpham().getId(),
					asp.getIdSanpham().getIdChitiet()));
		}
		model.addAttribute("thanhToan", thanhToanServiceImpl.findByIdDanhMucActive());
		model.addAttribute("vanChuyen", vanChuyenServiceImpl.findByIdDanhMucActive());
		model.addAttribute("nguoiDung", ng);
		model.addAttribute("tongSoSanPhamGioHang", chiTietDonHangServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoSanPhamYeuThich", sanPhamYeuThichServiceImpl.count(ng.getId()));
		model.addAttribute("tongSoTien", tongSoTien);
		model.addAttribute("listGioHang", listGioHang);
		return "frontend/thanhtoan";
	}

	@PostMapping(value = { "/banhang/checkout" })
	public String checkOut(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@RequestParam(name = "hoten") String hoten, @RequestParam(name = "email") String email,
			@RequestParam(name = "diachi") String diachi, @RequestParam(name = "dienthoai") String dienthoai,
			@RequestParam(name = "diachikhacid", required = true, defaultValue = "false") boolean checkbox,
			@RequestParam(name = "hotenkhac") String hotenkhac, @RequestParam(name = "diachikhac") String diachikhac,
			@RequestParam(name = "dienthoaikhac") String dienthoaikhac, @RequestParam(name = "ghichu") String ghichu,
			@RequestParam(name = "vanchuyen", required = true, defaultValue = "0") Integer vanchuyen,
			RedirectAttributes ra) throws MessagingException {
		boolean ktEmail = validate(email);
		if (checkbox) {
			if (hotenkhac.length() > 10 && hoten.length() < 50) {
				if (vanchuyen != 0) {
					if (diachikhac.length() > 20 && diachikhac.length() < 50) {
						if (dienthoaikhac.length() > 9 && dienthoaikhac.length() < 11
								&& dienthoaikhac.charAt(0) == '0') {
							Principal principal = request.getUserPrincipal();
							Nguoidung ng = new Nguoidung();
							ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
							model.addAttribute("tenNguoiDung", ng);
							Date date = new Date();
							Donhang dh = new Donhang();
							Thanhtoan tt = new Thanhtoan();
							tt = thanhToanServiceImpl.findById(2).get();
							Vanchuyen vc = new Vanchuyen();
							vc = vanChuyenServiceImpl.findById(vanchuyen).get();
							double tongSoTien = 0;
							List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
							for (Chitietdonhang ct : list) {
								tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());

							}
							ng.setHoten(hoten);
							ng.setDiachi(diachi);
							ng.setDienthoai(dienthoai);
							nguoiDungServiceImpl.update(ng);
							dh.setDiachi(diachikhac);
							dh.setDienthoai(dienthoaikhac);
							dh.setEmail(email);
							dh.setGhichu(ghichu);
							int max = (donHangServiceImpl.findMaxID());
							String NhanVienID = "DHMT" + (String.format("%04d", max + 1));
							dh.setIdChitietdonhang(NhanVienID);
							dh.setIdNguoidung(ng);
							dh.setNgaytao(date);
							dh.setStatus(1);
							dh.setTennguoinhan(hotenkhac);
							dh.setThanhtoan(tt);
							dh.setTongsotien(tongSoTien);
							dh.setTrangthaidonhang(0);
							dh.setTrangthaithanhtoan(false);
							dh.setVanchuyen(vc);
							donHangServiceImpl.save(dh);

							for (Chitietdonhang ct : list) {
								Chitietdonhang ctdh = new Chitietdonhang();
								ctdh = chiTietDonHangServiceImpl.findById(ct.getId()).get();
								ctdh.setIdDonhang(dh);
								chiTietDonHangServiceImpl.save(ctdh);
							}
							ra.addFlashAttribute("status", "Đặt hàng thành công. Cảm ơn bạn");
							StringBuffer templateMailBodyTable1Val = new StringBuffer();
							Locale localeEN = new Locale("vi", "VN");
							NumberFormat en = NumberFormat.getInstance(localeEN);
							String tongsotien = en.format(tongSoTien);
							int index = 1;
							for (Chitietdonhang ct : list) {
								String str1 = en.format(ct.getGiasanpham());
								String str2 = en.format(ct.getGiasanpham() * ct.getSoluong());
								templateMailBodyTable1Val.append("<tr><td>");
								templateMailBodyTable1Val.append(index);
								templateMailBodyTable1Val.append("</td>");
								index = index + 1;
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(ct.getIdSanpham().getTen());
								templateMailBodyTable1Val.append("</td>");
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(str1+" đ");
								templateMailBodyTable1Val.append("</td>");
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(ct.getSoluong());
								templateMailBodyTable1Val.append("</td>");
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(str2+ "đ");
								templateMailBodyTable1Val.append("</td></tr>");
							}

							MimeMessage msg = javaMailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(msg, true);
							helper.setTo(email);
							helper.setSubject("Thông tin đơn hàng");
							helper.setText("<html><body><h3>Thông tin đơn hàng: "+dh.getIdChitietdonhang()+"<h3>"
									+ "<table style='border:1px solid black'> <thead><tr><th scope='col'>STT</th><th scope='col'>Tên sản phẩm</th> <th scope='col'>Giá sản phẩm</th><th scope='col'>số lượng</th><th scope='col'>Thành tiền</th></tr></thead><tbody>"
									+ templateMailBodyTable1Val+"</tbody></table><br><h3>Tổng số tiền: "+tongsotien+"đ"+"<h3>", true);
							javaMailSender.send(msg);

							ra.addFlashAttribute("status", "Đặt hàng thành công. Cảm ơn bạn");
							return "redirect:/banhang/giohang";
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
							Principal principal = request.getUserPrincipal();
							Nguoidung ng = new Nguoidung();
							ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
							model.addAttribute("tenNguoiDung", ng);
							Date date = new Date();
							Donhang dh = new Donhang();
							Thanhtoan tt = new Thanhtoan();
							tt = thanhToanServiceImpl.findById(2).get();
							Vanchuyen vc = new Vanchuyen();
							vc = vanChuyenServiceImpl.findById(vanchuyen).get();
							ng.setHoten(hoten);
							ng.setDiachi(diachi);
							ng.setDienthoai(dienthoai);
							nguoiDungServiceImpl.update(ng);
							double tongSoTien = 0;
							List<Chitietdonhang> list = chiTietDonHangServiceImpl.findAllByGioHang(ng.getId());
							for (Chitietdonhang ct : list) {
								tongSoTien = tongSoTien + (ct.getGiasanpham() * ct.getSoluong());

							}
							dh.setDiachi(diachi);
							dh.setDienthoai(dienthoai);
							dh.setEmail(email);
							dh.setGhichu(ghichu);
							int max = (donHangServiceImpl.findMaxID());
							String NhanVienID = "DHMT" + (String.format("%04d", max + 1));
							dh.setIdChitietdonhang(NhanVienID);
							dh.setIdNguoidung(ng);
							dh.setNgaytao(date);
							dh.setStatus(1);
							dh.setTennguoinhan(hoten);
							dh.setThanhtoan(tt);
							dh.setTongsotien(tongSoTien);
							dh.setTrangthaidonhang(0);
							dh.setTrangthaithanhtoan(false);
							dh.setVanchuyen(vc);
							donHangServiceImpl.save(dh);

							for (Chitietdonhang ct : list) {
								Chitietdonhang ctdh = new Chitietdonhang();
								ctdh = chiTietDonHangServiceImpl.findById(ct.getId()).get();
								ctdh.setIdDonhang(dh);
								chiTietDonHangServiceImpl.save(ctdh);
							}
							StringBuffer templateMailBodyTable1Val = new StringBuffer();
							Locale localeEN = new Locale("vi", "VN");
							NumberFormat en = NumberFormat.getInstance(localeEN);
							String tongsotien = en.format(tongSoTien);
							int index = 1;
							for (Chitietdonhang ct : list) {
								String str1 = en.format(ct.getGiasanpham());
								String str2 = en.format(ct.getGiasanpham() * ct.getSoluong());
								templateMailBodyTable1Val.append("<tr><td>");
								templateMailBodyTable1Val.append(index);
								templateMailBodyTable1Val.append("</td>");
								index = index + 1;
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(ct.getIdSanpham().getTen());
								templateMailBodyTable1Val.append("</td>");
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(str1+" đ");
								templateMailBodyTable1Val.append("</td>");
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(ct.getSoluong());
								templateMailBodyTable1Val.append("</td>");
								templateMailBodyTable1Val.append("<td>");
								templateMailBodyTable1Val.append(str2+ "đ");
								templateMailBodyTable1Val.append("</td></tr>");
							}

							MimeMessage msg = javaMailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(msg, true);
							helper.setTo(email);
							helper.setSubject("Thông tin đơn hàng");
							helper.setText("<html><body><h3>Thông tin đơn hàng: "+dh.getIdChitietdonhang()+"<h3>"
									+ "<table style='border:1px solid black'> <thead><tr><th scope='col'>STT</th><th scope='col'>Tên sản phẩm</th> <th scope='col'>Giá sản phẩm</th><th scope='col'>số lượng</th><th scope='col'>Thành tiền</th></tr></thead><tbody>"
									+ templateMailBodyTable1Val+"</tbody></table><br><h3>Tổng số tiền: "+tongsotien+"đ"+"<h3>", true);
							javaMailSender.send(msg);

							ra.addFlashAttribute("status", "Đặt hàng thành công. Cảm ơn bạn");
							return "redirect:/banhang/giohang";
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

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
}