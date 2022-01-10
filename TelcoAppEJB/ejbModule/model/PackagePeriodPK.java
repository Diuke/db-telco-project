package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PackagePeriod database table.
 * 
 */
@Embeddable
public class PackagePeriodPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="package_id", insertable=false, updatable=false)
	private int packageId;

	@Column(name="period_id", insertable=false, updatable=false)
	private int periodId;

	public PackagePeriodPK() {
	}
	public int getPackageId() {
		return this.packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public int getPeriodId() {
		return this.periodId;
	}
	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PackagePeriodPK)) {
			return false;
		}
		PackagePeriodPK castOther = (PackagePeriodPK)other;
		return 
			(this.packageId == castOther.packageId)
			&& (this.periodId == castOther.periodId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.packageId;
		hash = hash * prime + this.periodId;
		
		return hash;
	}
}