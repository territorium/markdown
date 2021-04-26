/*
 * Copyright (c) 2001-2021 Territorium Online Srl / TOL GmbH. All Rights Reserved.
 *
 * This file contains Original Code and/or Modifications of Original Code as defined in and that are
 * subject to the Territorium Online License Version 1.0. You may not use this file except in
 * compliance with the License. Please obtain a copy of the License at http://www.tol.info/license/
 * and read it before using this file.
 *
 * The Original Code and all software distributed under the License are distributed on an 'AS IS'
 * basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, AND TERRITORIUM ONLINE HEREBY
 * DISCLAIMS ALL SUCH WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT. Please see the License for
 * the specific language governing rights and limitations under the License.
 */

package it.smartio.docs.fop.builder;

import it.smartio.docs.fop.template.FoMargin;

/**
 * The {@link FoSimplePageMaster} class.
 */
public class FoSimplePageMaster extends FoNode implements FoMargin<FoSimplePageMaster> {

	private final String name;
	private final FoRegion body;

	/**
	 * Constructs an instance of {@link FoSimplePageMaster}.
	 *
	 * @param name
	 */
	public FoSimplePageMaster(String name) {
		super("fo:simple-page-master");
		this.name = name;
		set("master-name", name);
		setPageSize("210mm", "297mm");
		this.body = new FoRegion();
		addNode(this.body);
	}

	public final String getPageName() {
		return this.name;
	}

	public void setPageSize(String width, String height) {
		setPageWidth(width);
		setPageHeight(height);
	}

	public void setPageWidth(String width) {
		set("page-width", width);
	}

	public void setPageHeight(String height) {
		set("page-height", height);
	}

	public FoRegion setBodyRegion(String name) {
		return this.body.setRegionName(name);
	}

	public FoRegion addRegionBefore(String name) {
		FoRegion region = new FoRegion(name, "before");
		addNode(region);
		return region;
	}

	public FoRegion addRegionAfter(String name) {
		FoRegion region = new FoRegion(name, "after");
		addNode(region);
		return region;
	}

	public FoRegion addRegionStart(String name) {
		FoRegion region = new FoRegion(name, "start");
		addNode(region);
		return region;
	}

	public FoRegion addRegionEnd(String name) {
		FoRegion region = new FoRegion(name, "end");
		addNode(region);
		return region;
	}
}
