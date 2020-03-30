export const enum PropertyType {
  STRING = 'STRING',
  INTEGER = 'INTEGER',
  DOUBLE = 'DOUBLE',
  TIME = 'TIME',
  DATE = 'DATE',
  DATETIME = 'DATETIME',
  TIMESTAMP = 'TIMESTAMP',
  BOOLEAN = 'BOOLEAN'
}

export interface ITransactionProperty {
  id?: number;
  code?: string;
  label?: string;
  propertyType?: PropertyType;
  active?: boolean;
}

export class TransactionProperty implements ITransactionProperty {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public propertyType?: PropertyType,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
