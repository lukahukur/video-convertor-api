
# Video Convertor

REST API that converts videos to formats supported by social networks

## Supported social networks
- TikTok
- Facebook
- Twitter

## API Reference

``/api/v1/facebook`` - converts video to facebook format

``/api/v1/tiktok`` - converts video to facebook tiktok

``/api/v1/twitter`` - converts video to facebook twitter

### Request
- **HTTP Method** - ``POST``

- **Headers** -
  ``Content-Type:application/json``
- **Body** -
  ```json
  {
    "url":"https://..."
  }
  ```

### Response
- **Headers**:
  ``Content-Type: text/plain;charset=UTF-8``

- **Body**:
  ``"https://s3.amazonaws.com/ads-campaigns-dev/ads-campaigns-media/123456789.mp4"``




## Social network video specs

``/api/v1/facebookConfig`` - video specs supported by facebook
``/api/v1/tiktokConfig`` - video specs supported by tiktok
``/api/v1/twitterConfig`` - video specs supported by twitter

### Request
- **HTTP Method** - ``GET``

### Response
- **Headers**:
  ``Content-Type: application/jsonn``

- **Body**:
 ```json
{
   "VIDEO_BITRATE": 1500,
   "AUDIO_BITRATE": 128,
   "VIDEO_CODEC": "libx264",
   "AUDIO_CODEC": "aac",
   "MIN_WIDTH": 120,
   "MIN_HEIGHT": 120,
   "MAX_VIDEO_DURATION": 240,
   "VIDEO_FORMAT": "mp4",
   "MAX_WIDTH": 1920,
   "SUPPORTED_ASPECT_RATIOS": [
      {
         "width": 1,
         "height": 1
      }
   ]
}
 ``` 

## Custom specs

``/api/v1/custom`` - converts video to user given specs.

### Request
- **HTTP Method** - ``POST``

- **Headers** -
  ``Content-Type:application/json``
- **Body** -
  ```json
  {
    "url":"https://...",
    "videoSpecifications":{
          "VIDEO_BITRATE": 1500,
          "AUDIO_BITRATE": 128,
          "VIDEO_CODEC": "libx264",
          "AUDIO_CODEC": "mp3",
          "MAX_VIDEO_DURATION": 2,
          "VIDEO_FORMAT": "mp4",
          "WIDTH":"1920",
          "HEIGHT":"1080"  
    }
  }
  ```
>**!!Note that not all values are supported. Please check [Supported video specifications](#supported-video-specifications)**

### Response
- **Headers**:
  ``Content-Type: text/plain;charset=UTF-8``

- **Body**:
  ``"https://s3.amazonaws.com/ads-campaigns-dev/ads-campaigns-media/123456789.mp4"``


## Supported video specifications

``/api/v1/supportedSpecs`` - supported spces endpoint

### Request
- **HTTP Method** - ``GET``

### Response
- **Headers**:
  ``Content-Type: application/json``

- **Body**:
    ```json 
    {
      "commonVideoFormats":["mp4","mov"],
      "audioCodecs":["aac"],
      "videoCodecs":["libx264"],
      "videoBitrates":[500,1000,1500],
      "audioBitrates":[94,128],
      "sizes":[[120,120],[640,480]]
    }
    ```