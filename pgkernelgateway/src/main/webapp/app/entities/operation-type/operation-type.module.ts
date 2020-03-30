import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  OperationTypeComponent,
  OperationTypeDetailComponent,
  OperationTypeUpdateComponent,
  OperationTypeDeletePopupComponent,
  OperationTypeDeleteDialogComponent,
  operationTypeRoute,
  operationTypePopupRoute
} from './';

const ENTITY_STATES = [...operationTypeRoute, ...operationTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OperationTypeComponent,
    OperationTypeDetailComponent,
    OperationTypeUpdateComponent,
    OperationTypeDeleteDialogComponent,
    OperationTypeDeletePopupComponent
  ],
  entryComponents: [
    OperationTypeComponent,
    OperationTypeUpdateComponent,
    OperationTypeDeleteDialogComponent,
    OperationTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayOperationTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
