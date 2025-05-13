<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

    <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administração</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js" integrity="sha512-pHVGpX7F/27yZ0ISY+VVjyULApbDlD0/X0rgGbTqCE7WFW5MezNTWG/dnhtbBuICzsd0WQPgpE4REBLv+UqChw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js" integrity="sha512-37T7leoNS06R80c8Ulq7cdCDU5MNQBwlYoy1TX/WUsLFC2eYNqtKlV0QjH7r8JpG/S0GUMZwebnVFLPd6SU5yg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<link href="fontawesome/css/all.css" rel="stylesheet">
<style>
	
    .navbar{
        background-color: #173B21 !important;
    }

    .modal{
        width: 300px;
        background-color: #173B21;
        color: white;
        font-size: 16px;
	    font-family: 'Roboto', sans-serif;
    }

    .modal-content {
        width: 300px;
        background-color: #173B21;
    }
    .modal-body {
        background-color: #173B21;
    }
     
    .list-group-item{
        background-color: #173B21;
        color: white;
    }

    .list-group-item:hover{
        background-color: white;
        color: #173B21;
    }
    
    #topico1{
        background-color: #ECBD69;
    }

    #topico2{
        background-color: #ECBD69;
    }

    #topico3{
        background-color: #ECBD69;
    }

    #topico4{
        background-color: #ECBD69;
    }
     
    li {
  	list-style-type: none;
	}
	
	.btn-custom {
	border-radius: 50px;
	}

	.btn-blue{
	background-color:#4e82dd; 
	color:#FFF;
	}
	
	.btn-green{
	background-color:#23d9c7; 
	color:#FFF;
	}
	
	.error {
		color: red
	}
	
	input.error, select.error{
		border: 1px solid red;
	}
	
	footer {
  width: 100%;
  height: 100px;
  background: #23d9c7;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  bottom: 0px;
  left: 0px;
  right: 0px;
}

</style>
</head>
