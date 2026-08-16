#ifndef _NETTLE_SHA_PORT_NETTLE_4_0_H_
#define _NETTLE_SHA_PORT_NETTLE_4_0_H_

#pragma message("included nettle/sha.h")

#include "sha1.h"
#include "sha2.h"
#include "sha3.h"
#define SHA1_DATA_SIZE SHA1_BLOCK_SIZE
#define SHA224_DATA_SIZE SHA224_BLOCK_SIZE
#define SHA256_DATA_SIZE SHA256_BLOCK_SIZE
#define SHA384_DATA_SIZE SHA384_BLOCK_SIZE
#define SHA512_DATA_SIZE SHA512_BLOCK_SIZE

#include "port_nettle_4_0.h"

#endif
