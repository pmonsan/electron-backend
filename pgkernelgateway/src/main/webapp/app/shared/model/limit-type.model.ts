export const enum LimitValueType {
  STRING = 'STRING',
  INTEGER = 'INTEGER',
  DOUBLE = 'DOUBLE',
  DATE = 'DATE'
}

export interface ILimitType {
  id?: number;
  code?: string;
  label?: string;
  limitValueType?: LimitValueType;
  active?: boolean;
  periodicityId?: number;
  limitMeasureId?: number;
}

export class LimitType implements ILimitType {
  constructor(
    public id?: number,
    public code?: string,
    public label?: string,
    public limitValueType?: LimitValueType,
    public active?: boolean,
    public periodicityId?: number,
    public limitMeasureId?: number
  ) {
    this.active = this.active || false;
  }
}
