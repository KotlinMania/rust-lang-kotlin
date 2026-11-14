const candidates = [
    './build/Release/kmcore_native.node',
    './build/Debug/kmcore_native.node',
];
let loaded = null;
let lastErr = null;
for (const candidate of candidates) {
    try {
        loaded = require(candidate);
        break;
    } catch (err) {
        lastErr = err;
    }
}
if (!loaded) {
    throw new Error('Could not load kmcore_native addon: ' + (lastErr ? lastErr.message : 'not found'));
}
module.exports = loaded;
