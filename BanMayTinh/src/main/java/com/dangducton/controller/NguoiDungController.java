package com.dangducton.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dangducton.entities.Anhsanpham;
import com.dangducton.entities.Nguoidung;
import com.dangducton.entities.Role;
import com.dangducton.dto.TonKhoDTO;
import com.dangducton.repository.RoleRespository;
import com.dangducton.service.AnhSanPhamServiceImpl;
import com.dangducton.service.ChiTietDonHangServiceImpl;
import com.dangducton.service.CommentServiceImpl;
import com.dangducton.service.DanhMucServiceImpl;
import com.dangducton.service.DonHangServiceImpl;
import com.dangducton.service.NguoiDungServiceImpl;
import com.dangducton.service.NhapSanPhamServiceImpl;
import com.dangducton.service.SanPhamServiceImpl;
import com.dangducton.service.SanPhamYeuThichServiceImpl;
import com.dangducton.util.Pagination;

@Controller
public class NguoiDungController {

	@Autowired
	private RoleRespository roleRespository;
	@Autowired
	NguoiDungServiceImpl nguoiDungServiceImpl;
	@Autowired
	ChiTietDonHangServiceImpl chiTietDonHangServiceImpl;
	@Autowired
	DanhMucServiceImpl danhMucServiceImpl;
	@Autowired
	AnhSanPhamServiceImpl anhSanPhamServiceImpl;
	@Autowired
	SanPhamYeuThichServiceImpl sanPhamYeuThichServiceImpl;
	@Autowired
	DonHangServiceImpl donHangServiceImpl;
	@Autowired
	CommentServiceImpl commentServiceImpl;
	@Autowired
	SanPhamServiceImpl sanPhamServiceImpl;
	@Autowired
	NhapSanPhamServiceImpl nhapSanPhamServiceImpl;

	public static String uploadDirectory = System.getProperty("user.dir") + "/upload";

	@GetMapping(value = { "/admin/danhsachnguoidung" })
	public String findAllDanhMuc(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Danh sách người dùng");
		model.addAttribute("status", status);
		Page<Nguoidung> danTocPage = nguoiDungServiceImpl.findAllNguoiDung(pageable);
		Pagination<Nguoidung> page = new Pagination<Nguoidung>(danTocPage, "/admin/danhsachnguoidung");
		model.addAttribute("listDanhMuc", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Anhsanpham> danTocPage1 = anhSanPhamServiceImpl.findByIdAnhSanPhamActive1();
		List<TonKhoDTO> listTonKho1 = new ArrayList<TonKhoDTO>();
		for (Anhsanpham asp : danTocPage1) {
			Integer tongBan = null;
			tongBan = chiTietDonHangServiceImpl.groupBy(asp.getIdSanpham().getId());
			if (tongBan == null) {
			} else {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				if (tongNhap - tongBan < 5) {
					int tonKho = tongNhap - tongBan;
					listTonKho1.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, tongBan,
							tonKho, asp.getIdSanpham().getId()));
				} else {

				}
			}
		}
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/danhsachnguoidung";
	}

	@GetMapping(value = { "/quantrihethong/findonequantri/{id}" })
	public String findonequantri(@PathVariable("id") Integer id, Model model, @ModelAttribute("status") String status,
			HttpServletRequest request, @PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Thông tin người dùng");
		Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
		model.addAttribute("thongtinnguoidung", ng);
		Principal principal = request.getUserPrincipal();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Anhsanpham> danTocPage1 = anhSanPhamServiceImpl.findByIdAnhSanPhamActive1();
		List<TonKhoDTO> listTonKho1 = new ArrayList<TonKhoDTO>();
		for (Anhsanpham asp : danTocPage1) {
			Integer tongBan = null;
			tongBan = chiTietDonHangServiceImpl.groupBy(asp.getIdSanpham().getId());
			if (tongBan == null) {
			} else {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				if (tongNhap - tongBan < 5) {
					int tonKho = tongNhap - tongBan;
					listTonKho1.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, tongBan,
							tonKho, asp.getIdSanpham().getId()));
				} else {

				}
			}
		}
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/updatequantri";
	}

	@PostMapping(value = { "/quantrihethong/addNhanVienHeThong" })
	public String addNhanVienHeThong(Model model, @RequestParam("uploadingFiles") MultipartFile file,
			@RequestParam("hoten") String hoten, @RequestParam("email") String email,
			@RequestParam("dienthoai") String dienthoai, @RequestParam("diachi") String diachi,
			@RequestParam("gioitinh") int gioitinh, @RequestParam("matkhau") String matkhau,
			@RequestParam("idquyen") int idquyen, @RequestParam("nhaplaimatkhau") String nhaplaimatkhau,
			RedirectAttributes ra) throws ParseException, IOException {
		Nguoidung dbUser = nguoiDungServiceImpl.checkEmail(email);
		if (!file.isEmpty()) {
			if (dbUser == null) {
				Nguoidung nd = new Nguoidung();
				if (nhaplaimatkhau.equals(matkhau) == true) {
					if (hoten.length() >= 5) {
						if (nhaplaimatkhau.length() >= 8 && nhaplaimatkhau.length() >= 8) {
							if (idquyen == 1) {
								Role role = roleRespository.findByName("ROLE_ADMIN");
								if (role == null) {
									role = new Role();
									role.setTen("ROLE_ADMIN");
									role.setStatus(1);
									roleRespository.save(role);
								}
								BufferedOutputStream stream = null;
								try {
									String fileName = file.getOriginalFilename();
									String filePath = Paths.get(uploadDirectory, fileName).toString();
									stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
									stream.write(file.getBytes());
									stream.close();
								} catch (IOException e) {
									stream.close();
								}
								nd.setRoleCollection(Arrays.asList(role));
								nd.setHoten(hoten);
								nd.setPassword(matkhau);
								nd.setEmail(email);
								nd.setDienthoai(dienthoai);
								nd.setDiachi(diachi);
								nd.setGioitinh(gioitinh);
								nd.setAnh("/upload/" + file.getOriginalFilename());
								nguoiDungServiceImpl.save(nd);
								ra.addFlashAttribute("status", "Thêm thành công");
								return "redirect:/quantrihethong/danhsachquantri";
							}
							if (idquyen == 2) {
								Role role = roleRespository.findByName("ROLE_SUPPER");
								if (role == null) {
									role = new Role();
									role.setTen("ROLE_SUPPER");
									role.setStatus(1);
									roleRespository.save(role);
								}
								BufferedOutputStream stream = null;
								try {
									String fileName = file.getOriginalFilename();
									String filePath = Paths.get(uploadDirectory, fileName).toString();
									stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
									stream.write(file.getBytes());
									stream.close();
								} catch (IOException e) {
									stream.close();
								}

								nd.setRoleCollection(Arrays.asList(role));
								nd.setHoten(hoten);
								nd.setPassword(matkhau);
								nd.setEmail(email);
								nd.setDienthoai(dienthoai);
								nd.setDiachi(diachi);
								nd.setGioitinh(gioitinh);
								nd.setAnh("/upload/" + file.getOriginalFilename());
								nguoiDungServiceImpl.save(nd);
								ra.addFlashAttribute("status", "Thêm thành công");
								return "redirect:/quantrihethong/danhsachquantri";
							}
						} else {
							ra.addFlashAttribute("status", "Mật khẩu gồm ít nhất 8 ký tự");
							return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
						}
					} else {
						ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ họ tên");
						return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
					}
				} else {
					ra.addFlashAttribute("status", "Yêu cầu nhập lại mật khẩu");
					return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
				}
			} else {
				ra.addFlashAttribute("status", "Email này đã được đăng ký");
				return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
			}
		} else {
			if (dbUser == null) {
				Nguoidung nd = new Nguoidung();
				if (nhaplaimatkhau.equals(matkhau) == true) {
					if (hoten.length() >= 5) {
						if (nhaplaimatkhau.length() >= 8 && nhaplaimatkhau.length() >= 8) {
							if (idquyen == 1) {
								Role role = roleRespository.findByName("ROLE_ADMIN");
								if (role == null) {
									role = new Role();
									role.setTen("ROLE_ADMIN");
									role.setStatus(1);
									roleRespository.save(role);

								}
								nd.setRoleCollection(Arrays.asList(role));
								nd.setHoten(hoten);
								nd.setPassword(matkhau);
								nd.setEmail(email);
								nd.setDienthoai(dienthoai);
								nd.setDiachi(diachi);
								nd.setGioitinh(gioitinh);
								nguoiDungServiceImpl.save(nd);
								ra.addFlashAttribute("status", "Thêm thành công");
								return "redirect:/quantrihethong/danhsachquantri";
							}
							if (idquyen == 2) {
								Role role = roleRespository.findByName("ROLE_SUPPER");
								if (role == null) {
									role = new Role();
									role.setTen("ROLE_SUPPER");
									role.setStatus(1);
									roleRespository.save(role);

								}
								nd.setRoleCollection(Arrays.asList(role));
								nd.setHoten(hoten);
								nd.setPassword(matkhau);
								nd.setEmail(email);
								nd.setDienthoai(dienthoai);
								nd.setDiachi(diachi);
								nd.setGioitinh(gioitinh);
								nguoiDungServiceImpl.save(nd);
								ra.addFlashAttribute("status", "Thêm thành công");
								return "redirect:/quantrihethong/danhsachquantri";
							}
						} else {
							ra.addFlashAttribute("status", "Mật khẩu gồm ít nhất 8 ký tự");
							return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
						}
					} else {
						ra.addFlashAttribute("status", "Yêu cầu nhập đầy đủ họ tên");
						return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
					}
				} else {
					ra.addFlashAttribute("status", "Yêu cầu nhập lại mật khẩu");
					return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
				}
			} else {
				ra.addFlashAttribute("status", "Email này đã được đăng ký");
				return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
			}
		}
		return nhaplaimatkhau;
	}

	@PostMapping(value = { "/quantrihethong/updateNhanVienHeThong" })
	public String updateNhanVienHeThong(Model model, @RequestParam("uploadingFiles") MultipartFile file,
			@RequestParam("hoten") String hoten, @RequestParam("id") Integer id,
			@RequestParam("dienthoai") String dienthoai, @RequestParam("diachi") String diachi,
			@RequestParam("gioitinh") int gioitinh, @RequestParam("matkhau") String matkhau,
			@RequestParam("nhaplaimatkhau") String nhaplaimatkhau, RedirectAttributes ra)
			throws ParseException, IOException {
		if (!file.isEmpty()) {
			if (hoten.length() >= 5) {
				if (nhaplaimatkhau.equals(matkhau) == true && matkhau.length() >= 8 && nhaplaimatkhau.length() >= 8
						&& !nhaplaimatkhau.trim().isEmpty() && !matkhau.trim().isEmpty()) {
					Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
					BufferedOutputStream stream = null;
					try {
						String fileName = file.getOriginalFilename();
						String filePath = Paths.get(uploadDirectory, fileName).toString();
						stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
						stream.write(file.getBytes());
						stream.close();
					} catch (IOException e) {
						stream.close();
					}

					ng.setHoten(hoten);
					ng.setPassword(matkhau);
					ng.setDienthoai(dienthoai);
					ng.setDiachi(diachi);
					ng.setGioitinh(gioitinh);
					ng.setAnh("/upload/" + file.getOriginalFilename());
					nguoiDungServiceImpl.save(ng);
					ra.addFlashAttribute("status", "Thêm thành công");
					return "redirect:/quantrihethong/danhsachquantri";
				} else {
					Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
					BufferedOutputStream stream = null;
					try {
						String fileName = file.getOriginalFilename();
						String filePath = Paths.get(uploadDirectory, fileName).toString();
						stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
						stream.write(file.getBytes());
						stream.close();
					} catch (IOException e) {
						stream.close();
					}
					ng.setHoten(hoten);
					ng.setDienthoai(dienthoai);
					ng.setDiachi(diachi);
					ng.setGioitinh(gioitinh);
					ng.setAnh("/upload/" + file.getOriginalFilename());
					nguoiDungServiceImpl.update(ng);
					ra.addFlashAttribute("status", "Thêm thành công");
					return "redirect:/quantrihethong/danhsachquantri";
				}
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập thêm họ tên");
				return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
			}
		} else {
			if (hoten.length() >= 5) {
				if (nhaplaimatkhau.equals(matkhau) == true && matkhau.length() >= 8 && nhaplaimatkhau.length() >= 8
						&& !nhaplaimatkhau.trim().isEmpty() && !matkhau.trim().isEmpty()) {
					Nguoidung ng = nguoiDungServiceImpl.findById(id).get();

					ng.setHoten(hoten);
					ng.setPassword(matkhau);
					ng.setDienthoai(dienthoai);
					ng.setDiachi(diachi);
					ng.setGioitinh(gioitinh);
					nguoiDungServiceImpl.save(ng);
					ra.addFlashAttribute("status", "Thêm thành công");
					return "redirect:/quantrihethong/danhsachquantri";
				} else {
					Nguoidung ng = nguoiDungServiceImpl.findById(id).get();

					ng.setHoten(hoten);
					ng.setDienthoai(dienthoai);
					ng.setDiachi(diachi);
					ng.setGioitinh(gioitinh);
					nguoiDungServiceImpl.update(ng);
					ra.addFlashAttribute("status", "Thêm thành công");
					return "redirect:/quantrihethong/danhsachquantri";
				}
			} else {
				ra.addFlashAttribute("status", "Yêu cầu nhập thêm họ tên");
				return "redirect:/quantrihethong/initthemmoitnhanvienhethong";
			}
		}
	}

	@GetMapping(value = { "/quantrihethong/initthemmoitnhanvienhethong" })
	public String initthemmoitnhanvienhethong(Model model, @ModelAttribute("status") String status,
			HttpServletRequest request, @PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Thêm quản trị viên");
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Anhsanpham> danTocPage1 = anhSanPhamServiceImpl.findByIdAnhSanPhamActive1();
		List<TonKhoDTO> listTonKho1 = new ArrayList<TonKhoDTO>();
		for (Anhsanpham asp : danTocPage1) {
			Integer tongBan = null;
			tongBan = chiTietDonHangServiceImpl.groupBy(asp.getIdSanpham().getId());
			if (tongBan == null) {
			} else {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				if (tongNhap - tongBan < 5) {
					int tonKho = tongNhap - tongBan;
					listTonKho1.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, tongBan,
							tonKho, asp.getIdSanpham().getId()));
				} else {

				}
			}
		}
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/initthemmoitnhanvienhethong";
	}

	@GetMapping(value = { "/quantrihethong/danhsachquantri" })
	public String danhsachquantri(Model model, @ModelAttribute("status") String status, HttpServletRequest request,
			@PageableDefault(size = 10) Pageable pageable) {
		model.addAttribute("danhMuc", "Danh sách quản trị hệ thống");
		model.addAttribute("status", status);
		Page<Nguoidung> danTocPage = nguoiDungServiceImpl.findAllQuanTri(pageable);
		Pagination<Nguoidung> page = new Pagination<Nguoidung>(danTocPage, "/quantrihethong/danhsachquantri");
		model.addAttribute("listDanhMuc", page.getContent());
		model.addAttribute("page", page);
		int offset = (page.getNumber() - 1) * page.getSize();
		model.addAttribute("items", offset);
		Principal principal = request.getUserPrincipal();
		Nguoidung ng = new Nguoidung();
		ng = nguoiDungServiceImpl.findByEmailAndEnabled(principal.getName());
		model.addAttribute("anhnguoidung", ng.getAnh());
		model.addAttribute("userName", ng.getHoten());
		List<Anhsanpham> danTocPage1 = anhSanPhamServiceImpl.findByIdAnhSanPhamActive1();
		List<TonKhoDTO> listTonKho1 = new ArrayList<TonKhoDTO>();
		for (Anhsanpham asp : danTocPage1) {
			Integer tongBan = null;
			tongBan = chiTietDonHangServiceImpl.groupBy(asp.getIdSanpham().getId());
			if (tongBan == null) {
			} else {
				Integer tongNhap = nhapSanPhamServiceImpl.groupBy(asp.getIdSanpham().getId());
				if (tongNhap - tongBan < 5) {
					int tonKho = tongNhap - tongBan;
					listTonKho1.add(new TonKhoDTO(asp.getIdSanpham().getTen(), asp.getUrlAnh(), tongNhap, tongBan,
							tonKho, asp.getIdSanpham().getId()));
				} else {

				}
			}
		}
		model.addAttribute("listTonKho1", listTonKho1);
		model.addAttribute("soluongconlai", listTonKho1.size());
		return "admin/danhsachquantri";
	}

	@GetMapping(path = "/admin/updateNotActivateNguoiDung/{id}")
	public String updateNotActivate(@PathVariable("id") Integer id, RedirectAttributes ra) {
		Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
		ng.setStatus(0);
		nguoiDungServiceImpl.update(ng);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/danhsachnguoidung";
	}

	@GetMapping(path = "/admin/updateActivateNguoiDung/{id}")
	public String updateActivate(@PathVariable("id") Integer id, RedirectAttributes ra) {
		Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
		ng.setStatus(1);
		nguoiDungServiceImpl.update(ng);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/admin/danhsachnguoidung";
	}
	
	@GetMapping(path = "/quantrihethong/updateNotActivateNguoiDung/{id}")
	public String updateNotActivatequantrihethong(@PathVariable("id") Integer id, RedirectAttributes ra) {
		Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
		ng.setStatus(0);
		nguoiDungServiceImpl.update(ng);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/quantrihethong/danhsachquantri";
	}

	@GetMapping(path = "/quantrihethong/updateActivateNguoiDung/{id}")
	public String updateActivatequantrihethong(@PathVariable("id") Integer id, RedirectAttributes ra) {
		Nguoidung ng = nguoiDungServiceImpl.findById(id).get();
		ng.setStatus(1);
		nguoiDungServiceImpl.update(ng);
		ra.addFlashAttribute("status", "Update thành công");
		return "redirect:/quantrihethong/danhsachquantri";
	}
}
