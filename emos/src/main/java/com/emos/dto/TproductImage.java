package com.emos.dto;
	
	public class TproductImage implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Integer id;
		private Tproduct product;
		private String imageSuffix;
		private String imageUrl;
		private byte[] image;

		public TproductImage() {
		}

		public TproductImage(Tproduct product, String imageSuffix, byte[] image) {
			this.product = product;
			this.imageSuffix = imageSuffix;
			this.image = image;
		}

		public Integer getId() {
			return this.id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

	

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public Tproduct getProduct() {
			return this.product;
		}

		public void setProduct(Tproduct product) {
			this.product = product;
		}

		public String getImageSuffix() {
			return this.imageSuffix;
		}

		public void setImageSuffix(String imageSuffix) {
			this.imageSuffix = imageSuffix;
		}

		public byte[] getImage() {
			return this.image;
		}

		public void setImage(byte[] image) {
			this.image = image;
		}

	}

