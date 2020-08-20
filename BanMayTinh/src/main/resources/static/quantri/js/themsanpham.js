$('form[id="second_form"]').validate({
	  rules: { 
		  tensanpham: {
		      required: true,
		      minlength: 5,
		      maxlength: 50,
		},
		  gianhap: {
		      required: true,
		       number: true,
		},
		  giaban: {
		      required: true,
		       number: true,
		},
		  giakhuyenmai: {
		      number: true,
		},
		 motangan: {
		      required: true,
		      minlength: 5,
		      maxlength: 255,
		},
		 motachitiet: {
		      required: true,
		},
		 uploadingFiles: {
		      required: true,
		},
		 idnhacungcap: {
		      required: true,
		},
		 iddanhmuc: {
		      required: true,
		}
	  },
	  messages: {
		  tensanpham: {
			required:'Tên sản phẩm không được bỏ trống',
		    minlength:'Tên sản phẩm gồm 5 đến 50 kí tự',
		    maxlength:'Tên sản phẩm gồm 5 đến 50 kí tự',
		},
		  gianhap: {
			required:'Giá nhập không được bỏ trống',
		    number:'Yêu cầu nhập số',
		},
		  giaban: {
			required:'Giá nhập không được bỏ trống',
		    number:'Yêu cầu nhập số',
		},
		  giakhuyenmai: {
		  number:'Yêu cầu nhập số',
		},
		  motangan: {
			required:'Yêu cầu nhập mô tả ngắn cho sản phẩm',
			 minlength: 'Mô tả có ít nhất 5 ký tự',
		     maxlength: 'Gồm 255 ký tự',
		},
		  motachitiet: {
			required:'Yêu cầu nhập mô tả chi tiết cho sản phẩm',
		},
		  uploadingFiles: {
			required:'Chọn ảnh cho sản phẩm',
		},
			idnhacungcap: {
			required:'Yêu cầu chọn nhà cung cấp',
		},
		iddanhmuc: {
			required:'Yêu cầu chọn danh mục',
		}
	  },
	  submitHandler: function(form) {
	    form.submit();
	  }
	});

