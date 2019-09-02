package com.droidoxy.easymoneyrewards.model;

/**
 * Created by DroidOXY
 */
 
public class Transactions {

	private String Image,TnName,TnType, Cat1, Cat2, Amount,ReqTo,Status,StatusName,TnDate,TnTime,TnDateFull,TnId,TnAddText,View,url;

	public Transactions() {
	}

	public Transactions(String TnName, String TnType, String Cat1, String Cat2, String Amount, String ReqTo,String Status, String StatusName, String TnDate, String TnTime, String TnDateFull, String TnId, String TnAddText, String View, String Image, String url) {

		this.TnName = TnName;
		this.TnType = TnType;
		this.Cat1 = Cat1;
		this.Cat2 = Cat2;
		this.Amount = Amount;
		this.ReqTo = ReqTo;
		this.Status = Status;
		this.StatusName = StatusName;
		this.TnDate = TnDate;
		this.TnTime = TnTime;
		this.TnDateFull = TnDateFull;
		this.TnId = TnId;
		this.TnAddText = TnAddText;
		this.View = View;
		this.Image = Image;
		this.url = url;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String Image) {
		this.Image = Image;
	}

	public String getTnName() {
		return TnName;
	}

	public void setTnName(String TnName) {
		this.TnName = TnName;
	}

	public String getTnType() {
		return TnType;
	}

	public void setTnType(String TnType) {
		this.TnType = TnType;
	}

	public String getCat1() {
		return Cat1;
	}

	public void setCat1(String Cat1) {
		this.Cat1 = Cat1;
	}

	public String getCat2() {
		return Cat2;
	}

	public void setCat2(String Cat2) {
		this.Cat2 = Cat2;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String Amount) {
		this.Amount = Amount;
	}

	public String getReqTo() {
		return ReqTo;
	}

	public void setReqTo(String ReqTo) {
		this.ReqTo = ReqTo;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public String getStatusName() {
		return StatusName;
	}

	public void setStatusName(String StatusName) {
		this.StatusName = StatusName;
	}

	public String getTnDate() {
		return TnDate;
	}

	public void setTnDate(String TnDate) {
		this.TnDate = TnDate;
	}

	public String getTnTime() {
		return TnTime;
	}

	public void setTnTime(String TnTime) {
		this.TnTime = TnTime;
	}

	public String getTnDateFull() {
		return TnDateFull;
	}

	public void setTnDateFull(String TnDateFull) {
		this.TnDateFull = TnDateFull;
	}

	public String getTnId() {
		return TnId;
	}

	public void setTnId(String TnId) {
		this.TnId = TnId;
	}

	public String getTnAddText() {
		return TnAddText;
	}

	public void setTnAddText(String TnAddText) {
		this.TnAddText = TnAddText;
	}

	public String getView() {
		return View;
	}

	public void setView(String View) {
		this.View = View;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}