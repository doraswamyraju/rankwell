/**
 * Utility to parse Google Maps Links and extract Place IDs or Business Identifiers.
 */

/**
 * Resolves redirects for shortened links (e.g., maps.app.goo.gl).
 */
const resolveRedirect = async (url) => {
  try {
    const response = await fetch(url, {
      method: 'HEAD',
      redirect: 'manual'
    });

    const redirectUrl = response.headers.get('location');
    if (redirectUrl) {
      // Recursively follow redirect if the response redirects again
      if (redirectUrl.includes('maps.app.goo.gl') || redirectUrl.includes('goo.gl/maps')) {
        return await resolveRedirect(redirectUrl);
      }
      return redirectUrl;
    }
    return url;
  } catch (error) {
    console.error('Failed to resolve URL redirect:', error.message);
    return url;
  }
};

/**
 * Parses Place ID or metadata from a Google Maps URL.
 */
const extractPlaceId = async (inputUrl) => {
  if (!inputUrl) return null;

  let targetUrl = inputUrl.trim();

  // Resolve short URLs
  if (targetUrl.includes('maps.app.goo.gl') || targetUrl.includes('goo.gl/maps')) {
    targetUrl = await resolveRedirect(targetUrl);
  }

  // 1. Match direct placeid query parameter
  const placeIdMatch = targetUrl.match(/[?&]placeid=([^&]+)/);
  if (placeIdMatch) {
    return placeIdMatch[1];
  }

  // 2. Match CID or LUDOCID query parameters (often maps redirects to these)
  const ludocidMatch = targetUrl.match(/[?&]ludocid=([^&]+)/);
  const fidMatch = targetUrl.match(/[?&]fid=([^&]+)/);
  
  // 3. Extract Place ID from the path (e.g., /maps/place/Name/data=!4m2!3m1!1s0x...:0x[PlaceId])
  // Google encodes place identifiers inside data parameter strings starting with '1s0x' followed by hex digits.
  const dataHexMatch = targetUrl.match(/1s(0x[0-9a-fA-F]+:0x[0-9a-fA-F]+)/);
  if (dataHexMatch) {
    // Return the hex identifier pair
    return dataHexMatch[1];
  }

  // Fallback: If we couldn't extract a Place ID but it is a valid Google Maps url,
  // return a mock or search-query indicator so that we don't break, or search by query
  const placePathMatch = targetUrl.match(/\/maps\/place\/([^/]+)/);
  if (placePathMatch) {
    return decodeURIComponent(placePathMatch[1]);
  }

  return null;
};

/**
 * Generates direct Google Review Link based on placeId or query
 */
const generateReviewUrl = (placeId) => {
  if (!placeId) return null;
  // If it's a hex or alphanumeric Place ID
  if (placeId.startsWith('0x') || /^[a-zA-Z0-9_-]{20,}$/.test(placeId)) {
    return `https://search.google.com/local/writereview?placeid=${placeId}`;
  }
  // If it's a query (fallback)
  return `https://search.google.com/local/writereview?q=${encodeURIComponent(placeId)}`;
};

module.exports = {
  resolveRedirect,
  extractPlaceId,
  generateReviewUrl
};
