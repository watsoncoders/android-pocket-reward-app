package com.droidoxy.easymoneyrewards.model;

/**
 * Created by DroidOXY
 */
 
public class Payouts {

	private String Image,TnName,Message,Amount,ReqTo,Status,StatusName,TnDate,TnTime,TnDateFull,TnId,TnAddText,View,url;

	public Payouts() {
	}

	public Payouts(String TnName, String Message, String Amount, String ReqTo, String Status, String StatusName, String TnDate, String TnTime, String TnDateFull, String TnId, String TnAddText, String View, String Image, String url) {

		this.TnName = TnName;
		this.Message = Message;
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

	public String getPayoutName() {
		return TnName;
	}

	public void setPayoutName(String TnName) {
		this.TnName = TnName;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String Amount) {
		this.Amount = Amount;
	}

	public String getReqPoints() {
		return ReqTo;
	}

	public void setReqPoints(String ReqTo) {
		this.ReqTo = ReqTo;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public String getSubtitle() {
		return StatusName;
	}

	public void setSubtitle(String StatusName) {
		this.StatusName = StatusName;
	}

	public String getPayoutMessage() {
		return Message;
	}

	public void setPayoutMessage(String Message) {
		this.Message = Message;
	}

	public String getPayoutId() {
		return TnId;
	}

	public void setPayoutId(String TnId) {
		this.TnId = TnId;
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