/**
 * Copyright (C) 2016 Matthieu Brouillard [http://oss.brouillard.fr/jgitver]
 * (matthieu@brouillard.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package it.smartio.docs.markdown.alerts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Alert {

	NOTE, SUCCESS, WARNING, ERROR;

	private static final Pattern ALERT_LINE = Pattern.compile("^(!(!?)(\\w+)\\s+).*");

	static Matcher matcher(CharSequence text) {
		return Alert.ALERT_LINE.matcher(text);
	}

	static Alert of(String text) {
		if ((text == null) || (text.trim().length() == 0)) {
			return NOTE;
		}

		switch (text.toLowerCase().charAt(0)) {
		case 'e': // Red, Error
			return ERROR;
		case 'w': // Yellow, Warning
			return WARNING;
		case 's': // Green, Success
			return SUCCESS;
		case 'n': // Blue, Info
		default:
			return NOTE;
		}
	}
}
