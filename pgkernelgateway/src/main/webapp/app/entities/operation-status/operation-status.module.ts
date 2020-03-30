import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  OperationStatusComponent,
  OperationStatusDetailComponent,
  OperationStatusUpdateComponent,
  OperationStatusDeletePopupComponent,
  OperationStatusDeleteDialogComponent,
  operationStatusRoute,
  operationStatusPopupRoute
} from './';

const ENTITY_STATES = [...operationStatusRoute, ...operationStatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OperationStatusComponent,
    OperationStatusDetailComponent,
    OperationStatusUpdateComponent,
    OperationStatusDeleteDialogComponent,
    OperationStatusDeletePopupComponent
  ],
  entryComponents: [
    OperationStatusComponent,
    OperationStatusUpdateComponent,
    OperationStatusDeleteDialogComponent,
    OperationStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayOperationStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
