package com.gome.rpc.register;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzy
 * @create 2021-08-13-20:51
 */
public class NewDemoException extends IOException {


    private static final long serialVersionUID = 8838482844757182536L;

    public NewDemoException(String message) {
        super (message);
    }



    @Deprecated
    public interface CodeDeprecated {
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#OK} instead
         */
        @Deprecated
        public static final int Ok = 0;

        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#SYSTEMERROR} instead
         */
        @Deprecated
        public static final int SystemError = -1;
        /**
         * @deprecated deprecated in 3.1.0, use
         * {@link KeeperException.Code#RUNTIMEINCONSISTENCY} instead
         */
        @Deprecated
        public static final int RuntimeInconsistency = -2;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#DATAINCONSISTENCY}
         * instead
         */
        @Deprecated
        public static final int DataInconsistency = -3;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#CONNECTIONLOSS}
         * instead
         */
        @Deprecated
        public static final int ConnectionLoss = -4;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#MARSHALLINGERROR}
         * instead
         */
        @Deprecated
        public static final int MarshallingError = -5;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#UNIMPLEMENTED}
         * instead
         */
        @Deprecated
        public static final int Unimplemented = -6;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#OPERATIONTIMEOUT}
         * instead
         */
        @Deprecated
        public static final int OperationTimeout = -7;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#BADARGUMENTS}
         * instead
         */
        @Deprecated
        public static final int BadArguments = -8;

        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#APIERROR} instead
         */
        @Deprecated
        public static final int APIError = -100;

        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#NONODE} instead
         */
        @Deprecated
        public static final int NoNode = -101;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#NOAUTH} instead
         */
        @Deprecated
        public static final int NoAuth = -102;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#BADVERSION} instead
         */
        @Deprecated
        public static final int BadVersion = -103;
        /**
         * @deprecated deprecated in 3.1.0, use
         * {@link KeeperException.Code#NOCHILDRENFOREPHEMERALS}
         * instead
         */
        @Deprecated
        public static final int NoChildrenForEphemerals = -108;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#NODEEXISTS} instead
         */
        @Deprecated
        public static final int NodeExists = -110;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#NOTEMPTY} instead
         */
        @Deprecated
        public static final int NotEmpty = -111;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#SESSIONEXPIRED} instead
         */
        @Deprecated
        public static final int SessionExpired = -112;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#INVALIDCALLBACK}
         * instead
         */
        @Deprecated
        public static final int InvalidCallback = -113;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#INVALIDACL} instead
         */
        @Deprecated
        public static final int InvalidACL = -114;
        /**
         * @deprecated deprecated in 3.1.0, use {@link KeeperException.Code#AUTHFAILED} instead
         */
        @Deprecated
        public static final int AuthFailed = -115;

        public static final int AuthFail = -116;
        /**
         * This value will be used directly in {@link CODE#SESSIONMOVED}
         */
        // public static final int SessionMoved = -118;
    }

    /** Codes which represent the various KeeperException
     * types. This enum replaces the deprecated earlier static final int
     * constants. The old, deprecated, values are in "camel case" while the new
     * enum values are in all CAPS.
     */
    public static enum Code implements CodeDeprecated {
        /** Everything is OK */
        OK (Ok),

        /** System and server-side errors.
         * This is never thrown by the server, it shouldn't be used other than
         * to indicate a range. Specifically error codes greater than this
         * value, but lesser than {@link #APIERROR}, are system errors.
         */
        SYSTEMERROR (SystemError),

        /** A runtime inconsistency was found */
        RUNTIMEINCONSISTENCY (RuntimeInconsistency),
        /** A data inconsistency was found */
        DATAINCONSISTENCY (DataInconsistency),
        /** Connection to the server has been lost */
        CONNECTIONLOSS (ConnectionLoss),
        /** Error while marshalling or unmarshalling data */
        MARSHALLINGERROR (MarshallingError),
        /** Operation is unimplemented */
        UNIMPLEMENTED (Unimplemented),
        /** Operation timeout */
        OPERATIONTIMEOUT (OperationTimeout),
        /** Invalid arguments */
        BADARGUMENTS (BadArguments),

        /** API errors.
         * This is never thrown by the server, it shouldn't be used other than
         * to indicate a range. Specifically error codes greater than this
         * value are API errors (while values less than this indicate a
         * {@link #SYSTEMERROR}).
         */
        APIERROR (APIError),

        /** Node does not exist */
        NONODE (NoNode),
        /** Not authenticated */
        NOAUTH (NoAuth),
        /** Version conflict */
        BADVERSION (BadVersion),
        /** Ephemeral nodes may not have children */
        NOCHILDRENFOREPHEMERALS (NoChildrenForEphemerals),
        /** The node already exists */
        NODEEXISTS (NodeExists),
        /** The node has children */
        NOTEMPTY (NotEmpty),
        /** The session has been expired by the server */
        SESSIONEXPIRED (SessionExpired),
        /** Invalid callback specified */
        INVALIDCALLBACK (InvalidCallback),
        /** Invalid ACL specified */
        INVALIDACL (InvalidACL),
        /** Client authentication failed */
        AUTHFAILED (AuthFailed),
        /** Session moved to another server, so operation is ignored */
        SESSIONMOVED (-118),
        /** State-changing request is passed to read-only server */
        NOTREADONLY (-119);

        private static final Map<Integer,Code> lookup = new HashMap<Integer, Code>();

        static {
            EnumSet<Code> codes = EnumSet.allOf (Code.class);
            for (Code code : codes) {
                lookup.put (code.code, code);
            }

        }

        private final int code;
        Code(int code) {
            this.code = code;
        }

        /**
         * Get the int value for a particular Code.
         * @return error code as integer
         */
        public int intValue() { return code; }

        /**
         * Get the Code value for a particular integer error code
         * @param code int error code
         * @return Code value corresponding to specified int code, or null
         */
        public static Code get(int code) {
            return lookup.get(code);
        }
    }

}
