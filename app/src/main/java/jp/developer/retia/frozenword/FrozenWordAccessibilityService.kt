package jp.developer.retia.frozenword

import android.accessibilityservice.AccessibilityService
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Browser
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


class FrozenWordAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {
    }

    private val previousUrlDetections: HashMap<String, Long> = HashMap()

    override fun onServiceConnected() {
        serviceInfo.packageNames = packageNames()
    }

    private fun captureUrl(info: AccessibilityNodeInfo, config: SupportedBrowserConfig): String? {
        val nodes = info.findAccessibilityNodeInfosByViewId(config.addressBarId)
        if (nodes == null || nodes.size <= 0) return null

        val addressBarNodeInfo = nodes[0]
        val url = addressBarNodeInfo.text?.toString()
        addressBarNodeInfo.recycle()
        return url
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val parentNodeInfo = event.source ?: return
        val packageName = event.packageName.toString()
        val browserConfig =
            getSupportedBrowsers().firstOrNull { it.packageName == packageName } ?: return

        val capturedUrl = captureUrl(parentNodeInfo, browserConfig)
        parentNodeInfo.recycle()

        //we can't find a url. Browser either was updated or opened page without url text field
        capturedUrl ?: return

        val eventTime = event.eventTime
        val detectionId = "$packageName, and url $capturedUrl"
        val lastRecordedTime =
            if (previousUrlDetections.containsKey(detectionId)) previousUrlDetections[detectionId]!! else 0
        //some kind of redirect throttling
        if (eventTime - lastRecordedTime > 2000) {
            previousUrlDetections[detectionId] = eventTime
            analyzeCapturedUrl(capturedUrl, browserConfig.packageName)
        }
    }

    private fun analyzeCapturedUrl(capturedUrl: String, browserPackage: String) {
        doAnything(capturedUrl, browserPackage)
    }

    /** we just reopen the browser app with our redirect url using service context
     * We may use more complicated solution with invisible activity to send a simple intent to open the url  */
    private fun doAnything(redirectUrl: String, browserPackage: String) {
        performRedirect("about:blank", browserPackage)
    }

    private fun packageNames(): Array<String> {
        return getSupportedBrowsers().map { it.packageName }.toTypedArray()
    }

    private class SupportedBrowserConfig(val packageName: String, val addressBarId: String)

    /** @return a list of supported browser configs
     * This list could be instead obtained from remote server to support future browser updates without updating an app
     */
    private fun getSupportedBrowsers(): List<SupportedBrowserConfig> {
        return listOf(
            SupportedBrowserConfig("com.android.chrome", "com.android.chrome:id/url_bar"),
            SupportedBrowserConfig(
                "org.mozilla.firefox",
                "org.mozilla.firefox:id/url_bar_title"
            )
        )
    }

    private fun performRedirect(redirectUrl: String, browserPackage: String) {
        try {
            val intent0 = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl))
                .setPackage(browserPackage)
                .putExtra(Browser.EXTRA_APPLICATION_ID, browserPackage)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val intent1 = MainActivity.createIntent(this)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivities(arrayOf(intent0, intent1))
        } catch (e: ActivityNotFoundException) {
            println(e)
        }
    }
}