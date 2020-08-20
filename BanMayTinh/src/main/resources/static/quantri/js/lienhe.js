$(document).ready(function () {
    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); // return New or Edit
        if (text === 'Trả lời') {
            $.get(href, function (Lienhe, status) {
            	$('.myForm #id').val(Lienhe.id);
                $('.myForm #hoten').val(Lienhe.hoten);
                $('.myForm #email').val(Lienhe.email);
                $('.myForm #noidung').val(Lienhe.noidung);
            });
            $('.myForm #exampleModal').modal();
        }});
 
    
    

    $('.table .activateBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #updateRef').attr('href', href);
        $('#myModal').modal();
    });
});

$('form[id="second_form"]').validate({
	  rules: { 
		  tendanhmuc: {
		      required: true,
		      minlength: 5,
		      maxlength: 50,
		}
	  },
	  messages: {
		  tendanhmuc: {
			required:'Tên danh mục không được bỏ trống',
		    minlength:'Tên danh mục gồm 5 đến 50 kí tự',
		    maxlength:'Tên danh mục gồm 5 đến 50 kí tự',
		}
	  },
	  submitHandler: function(form) {
	    form.submit();
	  }
	});

$('form[id="second_form2"]').validate({
	  rules: { 
		  hoten: {
		      required: true,
		},
		  email: {
		      required: true,
		},
		  noidung: {
		      required: true,
		},
		  noidungtraloi: {
		      required: true,
		}
	  },
	  messages: {
		  hoten: {
			required:'Họ tên không được bỏ trống',
		
		},
			  email: {
			required:'Email không được bỏ trống',
		
		},
			  noidung: {
			required:'Nội dung không được bỏ trống',
		
		},
			  noidungtraloi: {
			required:'Nội dung trả lời không được bỏ trống',
		
		}
	  },
	  submitHandler: function(form) {
	    form.submit();
	  }
	});
