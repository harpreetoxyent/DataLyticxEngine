<ruleset name="workflowpattern0">
	<variable-list>
		<variable>
			<type>com.oxyent.datalyticx.DataLyticxEntity</type>
			<id>entity</id>
		</variable>
		<variable>
			<type>int</type>
			<id>fieldIndex</id>
		</variable>
	</variable-list>
	<rule name="CheckAccuracy">
		<salience> 0 </salience>
			<if>
				<condition-list>
					<condition name="cond1">
						(
							(entity.type = "Material Master")
						) 
					</condition>
				</condition-list>
			</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						entity.checkAccuracy()
					</consequence>
				</consequence-list>
			</then>
				<url> 
					"http://www.google.com" 
				</url>
	</rule>
	<rule name="CheckAccuracy">
		<salience> 0 </salience>
			<if>
				<condition-list>
					<condition name="cond1">
						(
							(entity.type = "Material Master")
						) 
					</condition>
				</condition-list>
			</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						entity.checkCompleteness()
					</consequence>
				</consequence-list>
			</then>
				<url> 
					"http://www.google.com" 
				</url>
	</rule>
	<rule name="CheckLotSize">
		<salience> 0 </salience>
			<if>
				<condition-list>
					<condition name="cond1">
						(
							(entity.fields("LotSize").value != "") && 
							(entity.fields("LotSize").value == multiple(25)
						) 
					</condition>
				</condition-list>
			</if>
			<then>
				<consequence-list>
					<consequence name="c1">
						entity.field.quality.accuracy=true
					</consequence>
				</consequence-list>
			</then>
				<url> 
					"http://www.google.com" 
				</url>
	</rule>
	<rule name="CheckMaterialType">
		<salience> 0 </salience>
		<if>
			<condition-list>
				<condition name="cond1">
					(
						entity.fields("LotSize").value == 5000 &&
						entity.fields("MaterialType").value == "corrugate"
					)
				</condition>
			</condition-list>
		</if>
		<then>
			<consequence-list>
				<consequence name="c1">
					entity.field.quality.accuracy=true
				</consequence>
			</consequence-list>
		</then>
		<url> 
			"http://www.google.com" 
		</url>
	</rule>
</ruleset>	
	
