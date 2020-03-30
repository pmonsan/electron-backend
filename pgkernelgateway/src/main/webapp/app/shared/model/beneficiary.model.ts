export interface IBeneficiary {
  id?: number;
  number?: string;
  isCompany?: boolean;
  firstname?: string;
  name?: string;
  aliasAccount?: string;
  baccBankCode?: string;
  baccBranchCode?: string;
  baccAccountNumber?: string;
  baccRibKey?: string;
  cardCvv2?: string;
  cardPan?: string;
  cardValidityDate?: string;
  isDmAccount?: boolean;
  momoAccountNumber?: string;
  beneficiaryRelationshipCode?: string;
  beneficiaryTypeCode?: string;
  active?: boolean;
  customerId?: number;
}

export class Beneficiary implements IBeneficiary {
  constructor(
    public id?: number,
    public number?: string,
    public isCompany?: boolean,
    public firstname?: string,
    public name?: string,
    public aliasAccount?: string,
    public baccBankCode?: string,
    public baccBranchCode?: string,
    public baccAccountNumber?: string,
    public baccRibKey?: string,
    public cardCvv2?: string,
    public cardPan?: string,
    public cardValidityDate?: string,
    public isDmAccount?: boolean,
    public momoAccountNumber?: string,
    public beneficiaryRelationshipCode?: string,
    public beneficiaryTypeCode?: string,
    public active?: boolean,
    public customerId?: number
  ) {
    this.isCompany = this.isCompany || false;
    this.isDmAccount = this.isDmAccount || false;
    this.active = this.active || false;
  }
}
