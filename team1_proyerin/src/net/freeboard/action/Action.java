package net.freeboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// Ŭ������ ��������� Ʋ
	// ��ü ���� �Ҽ�����  �������̽� ��ӹ��� Ŭ����   ��ü����
	// ���߻�� ���� 
	// ������� ��ü �������� �ٷ� ���
	//static final int A=10;
	// �޼���Ʋ => �߻�޼��� => ��ӹ��� Ŭ���� ������ �޼��� �������̵�
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception;
}