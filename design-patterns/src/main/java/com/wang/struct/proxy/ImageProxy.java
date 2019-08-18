package com.wang.struct.proxy;

/**
 * ImageProxy 同 Image 有相同的接口
 */
public class ImageProxy implements Graphic{
	/** proxy 中需要保存对 Image 的引用 */
	private Image image;
	private String fileName;
	private String extend;
	private String imageAttr;
	public ImageProxy(String fileName) {
		this.fileName = fileName;
	}

	/** 延时实例化 image 对象 */
	protected Image getImage() {
		if (image == null) {
			image = new Image();
		}
		return image;
	}

	/** 在代理中保存Image的某个属性 */
	@Override
	public String getExtent() {
		if (extend == null) {
			extend = getImage().getExtent();
		}
		return extend;
	}

	@Override
	public void draw() {
		getImage().draw();
	}

	@Override
	public void handleMouse() {
		getImage().handleMouse();
	}

	@Override
	public void load() {
	}

	@Override
	public void save() {
	}
}
