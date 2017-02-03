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
@Table(name = "FORECAST_CALL_DAILY")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="forecastCallDailyJid", scope=ForecastCallDaily.class)
public class ForecastCallDaily implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5334885335115564456L;

	@Id
	@SequenceGenerator(name="FORECAST_CALL_DAILY_PK_FORECAST_CALL_DAILY_SEQ", sequenceName="FORECAST_CALL_DAILY_PK_FORECAST_CALL_DAILY_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="FORECAST_CALL_DAILY_PK_FORECAST_CALL_DAILY_SEQ")
	@Column(name = "PK_FORECAST_CALL_DAILY", unique = true, nullable = false)
	private Long pkForecastCallDaily;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_LOOKUP_ADDRESS", nullable=true)
	private LookupAddress lookupAddress;
	
	@Column(name="SUMMARY")
	private String summary;
	
	@Column(name="ICON")
	private String icon;
	
	@Column(name="SUNRISE_TIME")
	private Long sunriseTime;
	
	@Column(name="SUNSET_TIME")
	private Long sunsetTime;
	
	@Column(name="MOON_PHASE")
	private Long moonPhase;
	
	@Column(name="PRECIP_INTENSITY")
	private BigDecimal precipIntensity;
	
	@Column(name="PRECIP_INTENSITY_MAX")
	private BigDecimal precipIntensityMax;
	
	@Column(name="PRECIP_INTENSITY_MAX_TIME")
	private Long precipIntensityMaxTime;
	
	@Column(name="PRECIP_PROBABILITY")
	private BigDecimal precipProbability;
	
	@Column(name="PRECIP_TYPE")
	private String precipType;
	
	@Column(name="TEMPERATURE_MIN")
	private BigDecimal temperatureMin;
	
	@Column(name="TEMPERATURE_MIN_TIME")
	private Long temperatureMinTime;
	
	@Column(name="TEMPERATURE_MAX")
	private BigDecimal temperatureMax;
	
	@Column(name="TEMPERATURE_MAX_TIME")
	private Long temperatureMaxTime;
	
	@Column(name="APPARENT_TEMPERATURE_MIN")
	private BigDecimal apparentTemperatureMin;
	
	@Column(name="APPARENT_TEMPERATURE_MIN_TIME")
	private Long apparentTemperatureMinTime;
	
	@Column(name="APPARENT_TEMPERATURE_MAX")
	private BigDecimal apparentTemperatureMax;
	
	@Column(name="APPARENT_TEMPERATURE_MAX_TIME")
	private Long apparentTemperatureMaxTime;
	
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
	
	@Transient
	private Long time;
	
	@Transient
	private Integer temperatureCelcius;

	public Long getPkForecastCallDaily() {
		return pkForecastCallDaily;
	}
	public void setPkForecastCallDaily(Long pkForecastCallDaily) {
		this.pkForecastCallDaily = pkForecastCallDaily;
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

	public Long getSunriseTime() {
		return sunriseTime;
	}
	public void setSunriseTime(Long sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	public Long getSunsetTime() {
		return sunsetTime;
	}
	public void setSunsetTime(Long sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	public Long getMoonPhase() {
		return moonPhase;
	}
	public void setMoonPhase(Long moonPhase) {
		this.moonPhase = moonPhase;
	}

	public BigDecimal getPrecipIntensity() {
		return precipIntensity;
	}
	public void setPrecipIntensity(BigDecimal precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	public BigDecimal getPrecipIntensityMax() {
		return precipIntensityMax;
	}
	public void setPrecipIntensityMax(BigDecimal precipIntensityMax) {
		this.precipIntensityMax = precipIntensityMax;
	}
	
	public Long getPrecipIntensityMaxTime() {
		return precipIntensityMaxTime;
	}
	public void setPrecipIntensityMaxTime(Long precipIntensityMaxTime) {
		this.precipIntensityMaxTime = precipIntensityMaxTime;
	}
	
	public BigDecimal getPrecipProbability() {
		return precipProbability;
	}
	public void setPrecipProbability(BigDecimal precipProbability) {
		this.precipProbability = precipProbability;
	}
	
	public String getPrecipType() {
		return precipType;
	}
	public void setPrecipType(String precipType) {
		this.precipType = precipType;
	}
	
	public BigDecimal getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(BigDecimal temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public Long getTemperatureMinTime() {
		return temperatureMinTime;
	}
	public void setTemperatureMinTime(Long temperatureMinTime) {
		this.temperatureMinTime = temperatureMinTime;
	}

	public BigDecimal getTemperatureMax() {
		return temperatureMax;
	}
	public void setTemperatureMax(BigDecimal temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public Long getTemperatureMaxTime() {
		return temperatureMaxTime;
	}
	public void setTemperatureMaxTime(Long temperatureMaxTime) {
		this.temperatureMaxTime = temperatureMaxTime;
	}

	public BigDecimal getApparentTemperatureMin() {
		return apparentTemperatureMin;
	}
	public void setApparentTemperatureMin(BigDecimal apparentTemperatureMin) {
		this.apparentTemperatureMin = apparentTemperatureMin;
	}

	public Long getApparentTemperatureMinTime() {
		return apparentTemperatureMinTime;
	}
	public void setApparentTemperatureMinTime(Long apparentTemperatureMinTime) {
		this.apparentTemperatureMinTime = apparentTemperatureMinTime;
	}

	public BigDecimal getApparentTemperatureMax() {
		return apparentTemperatureMax;
	}
	public void setApparentTemperatureMax(BigDecimal apparentTemperatureMax) {
		this.apparentTemperatureMax = apparentTemperatureMax;
	}

	public Long getApparentTemperatureMaxTime() {
		return apparentTemperatureMaxTime;
	}
	public void setApparentTemperatureMaxTime(Long apparentTemperatureMaxTime) {
		this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
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
		if(this.temperatureMin != null) {
			try{				
				return Double.valueOf(NumericFunction.divide(NumericFunction.substract(temperatureMin.doubleValue(), 32d), 1.8d)).intValue();
			} catch (Exception e) {}
		}
		return temperatureCelcius;
	}
	@Transient
	public void setTemperatureCelcius(Integer temperatureCelcius) {
		this.temperatureCelcius = temperatureCelcius;
	}
	
}
