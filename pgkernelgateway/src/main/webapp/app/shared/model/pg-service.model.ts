export interface IPgService {
  id?: number;
  code?: string;
  name?: string;
  isNative?: boolean;
  isSourceInternal?: boolean;
  isDestinationInternal?: boolean;
  needSubscription?: boolean;
  currencyCode?: string;
  useTransactionType?: boolean;
  checkSubscription?: boolean;
  ignoreFees?: boolean;
  ignoreLimit?: boolean;
  ignoreCommission?: boolean;
  checkOtp?: boolean;
  pgTransactionType1Code?: string;
  pgTransactionType2Code?: string;
  partnerOwnerCode?: string;
  transactionTypeCode?: string;
  serviceAuthenticationCode?: string;
  contractPath?: string;
  description?: string;
  logic?: string;
  active?: boolean;
  sourceConnectorId?: number;
  destinationConnectorId?: number;
}

export class PgService implements IPgService {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public isNative?: boolean,
    public isSourceInternal?: boolean,
    public isDestinationInternal?: boolean,
    public needSubscription?: boolean,
    public currencyCode?: string,
    public useTransactionType?: boolean,
    public checkSubscription?: boolean,
    public ignoreFees?: boolean,
    public ignoreLimit?: boolean,
    public ignoreCommission?: boolean,
    public checkOtp?: boolean,
    public pgTransactionType1Code?: string,
    public pgTransactionType2Code?: string,
    public partnerOwnerCode?: string,
    public transactionTypeCode?: string,
    public serviceAuthenticationCode?: string,
    public contractPath?: string,
    public description?: string,
    public logic?: string,
    public active?: boolean,
    public sourceConnectorId?: number,
    public destinationConnectorId?: number
  ) {
    this.isNative = this.isNative || false;
    this.isSourceInternal = this.isSourceInternal || false;
    this.isDestinationInternal = this.isDestinationInternal || false;
    this.needSubscription = this.needSubscription || false;
    this.useTransactionType = this.useTransactionType || false;
    this.checkSubscription = this.checkSubscription || false;
    this.ignoreFees = this.ignoreFees || false;
    this.ignoreLimit = this.ignoreLimit || false;
    this.ignoreCommission = this.ignoreCommission || false;
    this.checkOtp = this.checkOtp || false;
    this.active = this.active || false;
  }
}
