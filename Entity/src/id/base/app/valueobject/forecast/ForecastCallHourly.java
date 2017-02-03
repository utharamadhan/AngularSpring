package id.base.app.valueobject.forecast;

import id.base.app.util.NumericFunction;
import id.base.app.valueobject.LookupAddress;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "FORECAST_CALL_HOURLY")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="forecastCallHourlyJid", scope=ForecastCallHourly.class)
public class ForecastCallHourly implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7550274704182895253L;
	
	@Id
	@SequenceGenerator(name="FORECAST_CALL_HOURLY_PK_FORECAST_CALL_HOURLY_SEQ", sequenceName="FORECAST_CALL_HOURLY_PK_FORECAST_CALL_HOURLY_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="FORECAST_CALL_HOURLY_PK_FORECAST_CALL_HOURLY_SEQ")
	@Column(name = "PK_FORECAST_CALL_HOURLY", unique = true, nullable = false)
	private Long pkForecastCallHourly;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_LOOKUP_ADDRESS", nullable=true)
	private LookupAddress lookupAddress;
	
	@Column(name="SUMMARY")
	private String summary;
	
	@Column(name="ICON")
	private String icon;
	
	@Column(name="PRECIP_TYPE")
	private String precipType;
	
	@Column(name="PRECIP_INTENSITY")
	private BigDecimal precipIntensity;
	
	@Column(name="PRECIP_PROBABILITY")
	private BigDecimal precipProbability;
	
	@Column(name="TEMPERATURE")
	private BigDecimal temperature;
	
	@Column(name="APPARENT_TEMPERATURE")
	private BigDecimal apparentTemperature;
	
	@Column(name="DEW_POINT")
	private BigDecimal dewPoint;
	
	@Column(name="HUMIDITY")
	private BigDecimal humidity;
	
	@Column(name="WIND_SPEED")
	private BigDecimal windSpeed;
	
	@Column(name="WIND_BEARING")
	private BigDecimal windBearing;
	
	@Column(name="VISIBILITY")
	private BigDecimal visibility;
	
	@Column(name="CLOUD_COVER")
	private BigDecimal cloudCover;
	
	@Column(name="PRESSURE")
	private BigDecimal pressure;
	
	@Column(name="OZONE")
	private BigDecimal ozone;

	@Column(name="FORECAST_DATE")
	private Date forecastDate;
	
	@Column(name="FORECAST_TIME")
	private Integer forecastTime;
	
	@Transient
	private Long time;
	
	@Transient
	private Integer temperatureCelcius;
	
	@Transient
	private Integer nextHour;
	
	public Long getPkForecastCallHourly() {
		return pkForecastCallHourly;
	}
	public void setPkForecastCallHourly(Long pkForecastCallHourly) {
		this.pkForecastCallHourly = pkForecastCallHourly;
	}
	
	public LookupAddress getLookupAddress() {
		return lookupAddress;
	}
	public void setLookupAddress(LookupAddress lookupAddress) {
		this.lookupAddress = lookupAddress;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getPrecipType() {
		return precipType;
	}
	public void setPrecipType(String precipType) {
		this.precipType = precipType;
	}
	
	public BigDecimal getPrecipIntensity() {
		return precipIntensity;
	}
	public void setPrecipIntensity(BigDecimal precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	public BigDecimal getPrecipProbability() {
		return precipProbability;
	}
	public void setPrecipProbability(BigDecimal precipProbability) {
		this.precipProbability = precipProbability;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	public BigDecimal getApparentTemperature() {
		return apparentTemperature;
	}
	public void setApparentTemperature(BigDecimal apparentTemperature) {
		this.apparentTemperature = apparentTemperature;
	}

	public BigDecimal getDewPoint() {
		return dewPoint;
	}
	public void setDewPoint(BigDecimal dewPoint) {
		this.dewPoint = dewPoint;
	}

	public BigDecimal getHumidity() {
		return humidity;
	}
	public void setHumidity(BigDecimal humidity) {
		this.humidity = humidity;
	}

	public BigDecimal getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(BigDecimal windSpeed) {
		this.windSpeed = windSpeed;
	}

	public BigDecimal getWindBearing() {
		return windBearing;
	}
	public void setWindBearing(BigDecimal windBearing) {
		this.windBearing = windBearing;
	}

	public BigDecimal getVisibility() {
		return visibility;
	}
	public void setVisibility(BigDecimal visibility) {
		this.visibility = visibility;
	}

	public BigDecimal getCloudCover() {
		return cloudCover;
	}
	public void setCloudCover(BigDecimal cloudCover) {
		this.cloudCover = cloudCover;
	}

	public BigDecimal getPressure() {
		return pressure;
	}
	public void setPressure(BigDecimal pressure) {
		this.pressure = pressure;
	}

	public BigDecimal getOzone() {
		return ozone;
	}
	public void setOzone(BigDecimal ozone) {
		this.ozone = ozone;
	}

	public Date getForecastDate() {
		return forecastDate;
	}
	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
	}

	public Integer getForecastTime() {
		return forecastTime;
	}
	public void setForecastTime(Integer forecastTime) {
		this.forecastTime = forecastTime;
	}
	
	@Transient
	public Long getTime() {
		return time;
	}
	@Transient
	public void setTime(Long time) {
		this.time = time;
	}
	
	@Transient
	public Integer getTemperatureCelcius() {
		if(this.temperature != null) {
			try{				
				return Double.valueOf(NumericFunction.divide(NumericFunction.substract(temperature.doubleValue(), 32d), 1.8d)).intValue();
			} catch (Exception e) {}
		}
		return temperatureCelcius;
	}
	@Transient
	public void setTemperatureCelcius(Integer temperatureCelcius) {
		this.temperatureCelcius = temperatureCelcius;
	}
	
	@Transient
	public Integer getNextHour() {
		if (forecastTime != null) {
			if (forecastTime < 23) {
				return forecastTime + 1;
			} else {
				return 0;
			}
		}
		return nextHour;
	}
	@Transient
	public void setNextHour(Integer nextHour) {
		this.nextHour = nextHour;
	}
}
