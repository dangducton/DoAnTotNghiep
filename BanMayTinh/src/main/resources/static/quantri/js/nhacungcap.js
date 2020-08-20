$(document).ready(function () {
    $('.table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); // return New or Edit
        if (text === 'Sửa') {
            $.get(href, function (Nhacungcap, status) {
            	$('.myForm #id').val(Nhacungcap.id);
                $('.myForm #tennhacungcap').val(Nhacungcap.ten);
                 $('.myForm #ngaytao').val(Nhacungcap.ngaytao);
                $('.myForm #status').val(Nhacungcap.status);
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
			required:'Tên nhà cung cấp không được bỏ trống',
		    minlength:'Tên nhà cung cấp gồm 5 đến 50 kí tự',
		    maxlength:'Tên nhà cung cấp gồm 5 đến 50 kí tự',
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
			required:'Tên nhà cung cấp không được bỏ trống',
		    minlength:'Tên nhà cung cấp gồm 5 đến 50 kí tự',
		    maxlength:'Tên nhà cung cấp gồm 5 đến 50 kí tự',
		}
	  },
	  submitHandler: function(form) {
	    form.submit();
	  }
	});
