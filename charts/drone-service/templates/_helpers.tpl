{{/*
Expand the name of the chart.
*/}}
{{- define "drone-service.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "drone-service.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
 get the name from pg chart doc
*/}}
{{- define "pg.fullname" -}}
{{- printf "%s-%s" .Release.Name .Values.pg.name | trunc 63 | trimSuffix "\n"}}
{{ end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "drone-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
secret
*/}}
{{- define "drone-service.secret" -}}
{{- printf "%s-%s" (include "drone-service.fullname" .)  "secret" }}
{{- end }}

{{/*
configmap
*/}}
{{- define "drone-service.configmap" -}}
{{- printf "%s-%s" (include "drone-service.fullname" .)  "configmap" }}
{{- end }}



{{/*
Common labels
*/}}
{{- define "drone-service.labels" -}}
helm.sh/chart: {{ include "drone-service.chart" . }}
{{ include "drone-service.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "drone-service.selectorLabels" -}}
app.kubernetes.io/name: {{ include "drone-service.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "drone-service.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "drone-service.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}
