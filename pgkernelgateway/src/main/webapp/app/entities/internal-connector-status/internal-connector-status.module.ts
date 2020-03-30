import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  InternalConnectorStatusComponent,
  InternalConnectorStatusDetailComponent,
  InternalConnectorStatusUpdateComponent,
  InternalConnectorStatusDeletePopupComponent,
  InternalConnectorStatusDeleteDialogComponent,
  internalConnectorStatusRoute,
  internalConnectorStatusPopupRoute
} from './';

const ENTITY_STATES = [...internalConnectorStatusRoute, ...internalConnectorStatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InternalConnectorStatusComponent,
    InternalConnectorStatusDetailComponent,
    InternalConnectorStatusUpdateComponent,
    InternalConnectorStatusDeleteDialogComponent,
    InternalConnectorStatusDeletePopupComponent
  ],
  entryComponents: [
    InternalConnectorStatusComponent,
    InternalConnectorStatusUpdateComponent,
    InternalConnectorStatusDeleteDialogComponent,
    InternalConnectorStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayInternalConnectorStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
