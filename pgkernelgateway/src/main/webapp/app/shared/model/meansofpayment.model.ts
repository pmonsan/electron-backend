export interface IMeansofpayment {
  id?: number;
  number?: string;
  aliasAccount?: string;
  baccBankCode?: string;
  baccBranchCode?: string;
  baccAccountNumber?: string;
  baccRibKey?: string;
  cardCvv2?: string;
  cardPan?: string;
  cardValidityDate?: string;
  momoAccount?: string;
  meansofpaymentTypeCode?: string;
  issuerCode?: string;
  active?: boolean;
  customerId?: number;
}

export class Meansofpayment implements IMeansofpayment {
  constructor(
    public id?: number,
    public number?: string,
    public aliasAccount?: string,
    public baccBankCode?: string,
    public baccBranchCode?: string,
    public baccAccountNumber?: string,
    public baccRibKey?: string,
    public cardCvv2?: string,
    public cardPan?: string,
    public cardValidityDate?: string,
    public momoAccount?: string,
    public meansofpaymentTypeCode?: string,
    public issuerCode?: string,
    public active?: boolean,
    public customerId?: number
  ) {
    this.active = this.active || false;
  }
}
