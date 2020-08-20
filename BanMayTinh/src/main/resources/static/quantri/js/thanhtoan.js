$(document).ready(function () {
    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); // return New or Edit
        if (text === 'Sửa') {
            $.get(href, function (Thanhtoan, status) {
            	$('.myForm #id').val(Thanhtoan.id);
                $('.myForm #tendanhmuc').val(Thanhtoan.ten);
                 $('.myForm #ngaytao').val(Thanhtoan.ngaytao);
                $('.myForm #status').val(Thanhtoan.status);
            });
            $('.myForm #exampleModal').modal();
        }});
 
    
    	$('.nBtn').on('click', function (event) {
    		event.preventDefault();
    		$('.myForm #tendanhmuc').val('');
            $('.myForm1 #exampleModal1').modal();
    	});
    

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
