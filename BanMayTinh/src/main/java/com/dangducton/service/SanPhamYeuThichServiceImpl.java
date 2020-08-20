package com.dangducton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangducton.entities.Sanphamyeuthich;
import com.dangducton.repository.SanPhamYeuThichRepository;

@Service
@Transactional
public class SanPhamYeuThichServiceImpl implements SanPhamYeuThichService{
	@Autowired(required = true)
	public SanPhamYeuThichRepository sanPhamYeuThichRepository;
	
	@Override
	public Iterable<Sanphamyeuthich> findAll() {
		// TODO Auto-generated method stub
		return sanPhamYeuThichRepository.findAll();
	}

	@Override
	public Optional<Sanphamyeuthich> findById(Integer id) {
		// TODO Auto-generated method stub
		return sanPhamYeuThichRepository.findById(id);
	}

	@Override
	public void save(Sanphamyeuthich sanPham) {
		// TODO Auto-generated method stub
		sanPhamYeuThichRepository.save(sanPham);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		sanPhamYeuThichRepository.deleteById(id);
	}

	@Override
	public List<Sanphamyeuthich> findAllByNguoiDung(Integer id) {
		// TODO Auto-generated method stub
		return sanPhamYeuThichRepository.findAllByNguoiDung(id);
	}

	@Override
	public Sanphamyeuthich findAllBySanPhamYeuThich(Integer id, Integer idSanpham) {
		// TODO Auto-generated method stub
		return sanPhamYeuThichRepository.findAllBySanPhamYeuThich(id, idSanpham);
	}

	@Override
	public Integer count(Integer id) {
		// TODO Auto-generated method stub
		return sanPhamYeuThichRepository.count(id);
	}

	@Override
	public long countsanphamyeuthich() {
		// TODO Auto-generated method stub
		return sanPhamYeuThichRepository.countsanphamyeuthich();
	}

}
