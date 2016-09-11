<?php

class Controllerauth extends CI_Controller{

	public function index(){
	
	}
	
	public function getdetails(){
		$this->load->model('Model');
		$data['details']=$this->Model->getDetails();
		$encoded_data=json_encode($data);
		echo $encoded_data;
	}
	
	public function register(){
		$name=$_GET['name'];
		$username=$_GET['username'];
		$password=$_GET['password'];
		$username=hash ( "sha256" , $username ,false );
		$password=hash ( "sha256" , $password ,false  );
		$this->load->model('Model');
		$data['registeredUserId']=$this->Model->register($name,$username,$password);
		$encoded_data=json_encode($data);
		echo $encoded_data;
	}
	
}
?>