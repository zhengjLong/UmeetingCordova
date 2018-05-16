cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "umeeting-plugin.umeeting-plugin",
        "file": "plugins/umeeting-plugin/www/umeeting-plugin.js",
        "pluginId": "umeeting-plugin",
        "clobbers": [
            "Umeeting"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.3",
    "umeeting-plugin": "1.0.0"
};
// BOTTOM OF METADATA
});