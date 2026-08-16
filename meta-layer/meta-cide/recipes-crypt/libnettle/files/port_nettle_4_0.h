#ifndef _PORT_NETTLE_4_0_H_
#define _PORT_NETTLE_4_0_H_

#include <nettle/hmac.h>
#include <nettle/sha2.h>

#define GCM_DIGEST_COMPAT(ctx, fn, size, tag) GCM_DIGEST(ctx, fn, size)

#define HMAC_DIGEST_COMPAT(ctx, DUMMY, length, output)        \
    do {                                                      \
        uint8_t _full_digest[SHA256_DIGEST_SIZE];             \
        hmac_sha256_digest(&(ctx), SHA256_DIGEST_SIZE, _full_digest); \
        memcpy((output), _full_digest, (length));             \
    } while (0)

#endif
