import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  CustomerTypeComponent,
  CustomerTypeDetailComponent,
  CustomerTypeUpdateComponent,
  CustomerTypeDeletePopupComponent,
  CustomerTypeDeleteDialogComponent,
  customerTypeRoute,
  customerTypePopupRoute
} from './';

const ENTITY_STATES = [...customerTypeRoute, ...customerTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomerTypeComponent,
    CustomerTypeDetailComponent,
    CustomerTypeUpdateComponent,
    CustomerTypeDeleteDialogComponent,
    CustomerTypeDeletePopupComponent
  ],
  entryComponents: [
    CustomerTypeComponent,
    CustomerTypeUpdateComponent,
    CustomerTypeDeleteDialogComponent,
    CustomerTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayCustomerTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
