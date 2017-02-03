package id.base.app.valueobject.forecast;

import java.util.List;

public class ForecastCallWrapper {
	
	public ForecastCallWrapper () {}
	
	private String latitude;
	
	private String longitude;
	
	private String timezone;
	
	private String offset;
	
	private CurrentlyResult currently;
	
	private MinutelyResult minutely;
	
	private HourlyResult hourly;
	
	private DailyResult daily;
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	
	public CurrentlyResult getCurrently() {
		return currently;
	}
	public void setCurrently(CurrentlyResult currently) {
		this.currently = currently;
	}

	public MinutelyResult getMinutely() {
		return minutely;
	}
	public void setMinutely(MinutelyResult minutely) {
		this.minutely = minutely;
	}

	public HourlyResult getHourly() {
		return hourly;
	}
	public void setHourly(HourlyResult hourly) {
		this.hourly = hourly;
	}

	public DailyResult getDaily() {
		return daily;
	}
	public void setDaily(DailyResult daily) {
		this.daily = daily;
	}

	class DailyResult {
		
		private String summary;
		
		private String icon;
		
		private List<DailyResultData> daily;
		
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

		public List<DailyResultData> getDaily() {
			return daily;
		}
		public void setDaily(List<DailyResultData> daily) {
			this.daily = daily;
		}

		class DailyResultData {
			
			private String time;
			private String summary;
			private String icon;
			private String sunriseTime;
			private String sunsetTime;
			private String moonPhase;
			private String precipIntensity;
			private String precipIntensityMax;
			private String precipIntensityMaxTime;
			private String precipProbability;
			private String precipType;
			private String temperatureMin;
			private String temperatureMinTime;
			private String temperatureMax;
			private String temperatureMaxTime;
			private String apparentTemperature;
			private String apparentTemperatureMin;
			private String apparentTemperatureMinTime;
			private String apparentTemperatureMax;
			private String apparentTemperatureMaxTime;
			private String dewPoint;
			private String humidity;
			private String windSpeed;
			private String windBearing;
			private String visibility;
			private String cloudCover;
			private String pressure;
			private String ozone;
			public String getTime() {
				return time;
			}
			public void setTime(String time) {
				this.time = time;
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
			public String getSunriseTime() {
				return sunriseTime;
			}
			public void setSunriseTime(String sunriseTime) {
				this.sunriseTime = sunriseTime;
			}
			public String getSunsetTime() {
				return sunsetTime;
			}
			public void setSunsetTime(String sunsetTime) {
				this.sunsetTime = sunsetTime;
			}
			public String getMoonPhase() {
				return moonPhase;
			}
			public void setMoonPhase(String moonPhase) {
				this.moonPhase = moonPhase;
			}
			public String getPrecipIntensity() {
				return precipIntensity;
			}
			public void setPrecipIntensity(String precipIntensity) {
				this.precipIntensity = precipIntensity;
			}
			public String getPrecipIntensityMax() {
				return precipIntensityMax;
			}
			public void setPrecipIntensityMax(String precipIntensityMax) {
				this.precipIntensityMax = precipIntensityMax;
			}
			public String getPrecipIntensityMaxTime() {
				return precipIntensityMaxTime;
			}
			public void setPrecipIntensityMaxTime(String precipIntensityMaxTime) {
				this.precipIntensityMaxTime = precipIntensityMaxTime;
			}
			public String getPrecipProbability() {
				return precipProbability;
			}
			public void setPrecipProbability(String precipProbability) {
				this.precipProbability = precipProbability;
			}
			public String getPrecipType() {
				return precipType;
			}
			public void setPrecipType(String precipType) {
				this.precipType = precipType;
			}
			public String getTemperatureMin() {
				return temperatureMin;
			}
			public void setTemperatureMin(String temperatureMin) {
				this.temperatureMin = temperatureMin;
			}
			public String getTemperatureMinTime() {
				return temperatureMinTime;
			}
			public void setTemperatureMinTime(String temperatureMinTime) {
				this.temperatureMinTime = temperatureMinTime;
			}
			public String getTemperatureMax() {
				return temperatureMax;
			}
			public void setTemperatureMax(String temperatureMax) {
				this.temperatureMax = temperatureMax;
			}
			public String getTemperatureMaxTime() {
				return temperatureMaxTime;
			}
			public void setTemperatureMaxTime(String temperatureMaxTime) {
				this.temperatureMaxTime = temperatureMaxTime;
			}
			public String getApparentTemperature() {
				return apparentTemperature;
			}
			public void setApparentTemperature(String apparentTemperature) {
				this.apparentTemperature = apparentTemperature;
			}
			public String getApparentTemperatureMin() {
				return apparentTemperatureMin;
			}
			public void setApparentTemperatureMin(String apparentTemperatureMin) {
				this.apparentTemperatureMin = apparentTemperatureMin;
			}
			public String getApparentTemperatureMinTime() {
				return apparentTemperatureMinTime;
			}
			public void setApparentTemperatureMinTime(String apparentTemperatureMinTime) {
				this.apparentTemperatureMinTime = apparentTemperatureMinTime;
			}
			public String getApparentTemperatureMax() {
				return apparentTemperatureMax;
			}
			public void setApparentTemperatureMax(String apparentTemperatureMax) {
				this.apparentTemperatureMax = apparentTemperatureMax;
			}
			public String getApparentTemperatureMaxTime() {
				return apparentTemperatureMaxTime;
			}
			public void setApparentTemperatureMaxTime(String apparentTemperatureMaxTime) {
				this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
			}
			public String getDewPoint() {
				return dewPoint;
			}
			public void setDewPoint(String dewPoint) {
				this.dewPoint = dewPoint;
			}
			public String getHumidity() {
				return humidity;
			}
			public void setHumidity(String humidity) {
				this.humidity = humidity;
			}
			public String getWindSpeed() {
				return windSpeed;
			}
			public void setWindSpeed(String windSpeed) {
				this.windSpeed = windSpeed;
			}
			public String getWindBearing() {
				return windBearing;
			}
			public void setWindBearing(String windBearing) {
				this.windBearing = windBearing;
			}
			public String getVisibility() {
				return visibility;
			}
			public void setVisibility(String visibility) {
				this.visibility = visibility;
			}
			public String getCloudCover() {
				return cloudCover;
			}
			public void setCloudCover(String cloudCover) {
				this.cloudCover = cloudCover;
			}
			public String getPressure() {
				return pressure;
			}
			public void setPressure(String pressure) {
				this.pressure = pressure;
			}
			public String getOzone() {
				return ozone;
			}
			public void setOzone(String ozone) {
				this.ozone = ozone;
			}
		}
		
	}
	
	class HourlyResult {
		private String summary;
		private String icon;
		private List<HourlyResultData> data;
		
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

		public List<HourlyResultData> getData() {
			return data;
		}
		public void setData(List<HourlyResultData> data) {
			this.data = data;
		}

		class HourlyResultData {
			private String time;
			private String summary;
			private String icon;
			private String precipIntensity;
			private String precipProbability;
			private String temperature;
			private String apparentTemperature;
			private String dewPoint;
			private String humidity;
			private String windSpeed;
			private String windBearing;
			private String visibility;
			private String cloudCover;
			private String pressure;
			private String ozone;
			public String getTime() {
				return time;
			}
			public void setTime(String time) {
				this.time = time;
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
			public String getPrecipIntensity() {
				return precipIntensity;
			}
			public void setPrecipIntensity(String precipIntensity) {
				this.precipIntensity = precipIntensity;
			}
			public String getPrecipProbability() {
				return precipProbability;
			}
			public void setPrecipProbability(String precipProbability) {
				this.precipProbability = precipProbability;
			}
			public String getTemperature() {
				return temperature;
			}
			public void setTemperature(String temperature) {
				this.temperature = temperature;
			}
			public String getApparentTemperature() {
				return apparentTemperature;
			}
			public void setApparentTemperature(String apparentTemperature) {
				this.apparentTemperature = apparentTemperature;
			}
			public String getDewPoint() {
				return dewPoint;
			}
			public void setDewPoint(String dewPoint) {
				this.dewPoint = dewPoint;
			}
			public String getHumidity() {
				return humidity;
			}
			public void setHumidity(String humidity) {
				this.humidity = humidity;
			}
			public String getWindSpeed() {
				return windSpeed;
			}
			public void setWindSpeed(String windSpeed) {
				this.windSpeed = windSpeed;
			}
			public String getWindBearing() {
				return windBearing;
			}
			public void setWindBearing(String windBearing) {
				this.windBearing = windBearing;
			}
			public String getVisibility() {
				return visibility;
			}
			public void setVisibility(String visibility) {
				this.visibility = visibility;
			}
			public String getCloudCover() {
				return cloudCover;
			}
			public void setCloudCover(String cloudCover) {
				this.cloudCover = cloudCover;
			}
			public String getPressure() {
				return pressure;
			}
			public void setPressure(String pressure) {
				this.pressure = pressure;
			}
			public String getOzone() {
				return ozone;
			}
			public void setOzone(String ozone) {
				this.ozone = ozone;
			}
		}
	}
	
	class MinutelyResult {
		private String summary;
		private String icon;
		private List<MinutelyResultData> data;
		
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

		public List<MinutelyResultData> getData() {
			return data;
		}
		public void setData(List<MinutelyResultData> data) {
			this.data = data;
		}

		class MinutelyResultData {
			private String time;
			private String precipIntensity;
			private String precipProbability;
			
			public String getTime() {
				return time;
			}
			public void setTime(String time) {
				this.time = time;
			}
			public String getPrecipIntensity() {
				return precipIntensity;
			}
			public void setPrecipIntensity(String precipIntensity) {
				this.precipIntensity = precipIntensity;
			}
			public String getPrecipProbability() {
				return precipProbability;
			}
			public void setPrecipProbability(String precipProbability) {
				this.precipProbability = precipProbability;
			}
		}
	}
	
	class CurrentlyResult {
		
		private String time;
		private String summary;
		private String icon;
		private String nearestStormDistance;
		private String nearestStormBearing;
		private String precipIntensity;
		private String precipProbability;
		private String temperature;
		private String apparentTemperature;
		private String dewPoint;
		private String humidity;
		private String windSpeed;
		private String windBearing;
		private String visibility;
		private String cloudCover;
		private String pressure;
		private String ozone;
		
		CurrentlyResult() {}
		
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
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
		public String getNearestStormDistance() {
			return nearestStormDistance;
		}
		public void setNearestStormDistance(String nearestStormDistance) {
			this.nearestStormDistance = nearestStormDistance;
		}
		public String getNearestStormBearing() {
			return nearestStormBearing;
		}
		public void setNearestStormBearing(String nearestStormBearing) {
			this.nearestStormBearing = nearestStormBearing;
		}
		public String getPrecipIntensity() {
			return precipIntensity;
		}
		public void setPrecipIntensity(String precipIntensity) {
			this.precipIntensity = precipIntensity;
		}
		public String getPrecipProbability() {
			return precipProbability;
		}
		public void setPrecipProbability(String precipProbability) {
			this.precipProbability = precipProbability;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
		public String getApparentTemperature() {
			return apparentTemperature;
		}
		public void setApparentTemperature(String apparentTemperature) {
			this.apparentTemperature = apparentTemperature;
		}
		public String getDewPoint() {
			return dewPoint;
		}
		public void setDewPoint(String dewPoint) {
			this.dewPoint = dewPoint;
		}
		public String getHumidity() {
			return humidity;
		}
		public void setHumidity(String humidity) {
			this.humidity = humidity;
		}
		public String getWindSpeed() {
			return windSpeed;
		}
		public void setWindSpeed(String windSpeed) {
			this.windSpeed = windSpeed;
		}
		public String getWindBearing() {
			return windBearing;
		}
		public void setWindBearing(String windBearing) {
			this.windBearing = windBearing;
		}
		public String getVisibility() {
			return visibility;
		}
		public void setVisibility(String visibility) {
			this.visibility = visibility;
		}
		public String getCloudCover() {
			return cloudCover;
		}
		public void setCloudCover(String cloudCover) {
			this.cloudCover = cloudCover;
		}
		public String getPressure() {
			return pressure;
		}
		public void setPressure(String pressure) {
			this.pressure = pressure;
		}
		public String getOzone() {
			return ozone;
		}
		public void setOzone(String ozone) {
			this.ozone = ozone;
		}
	}
}
