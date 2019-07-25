####1.登录时，密码验证通过，取当前时间戳生成签名Token，放在Response Header的Authorization属性中，同时在缓存中记录值为当前时间戳的RefreshToken，并设置有效期。
####2.客户端请求每次携带Token进行请求。
####3.服务端每次校验请求的Token有效后，同时比对Token中的时间戳与缓存中的RefreshToken时间戳是否一致，一致则判定Token有效。
####4.当请求的Token被验证时抛出TokenExpiredException异常时说明Token过期，校验时间戳一致后重新生成Token并调用登录方法。
####5.每次生成新的Token后，同时要根据新的时间戳更新缓存中的RefreshToken，以保证两者时间戳一致。