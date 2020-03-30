import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PgkernelgatewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PgkernelgatewaySharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PgkernelgatewaySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewaySharedModule {
  static forRoot() {
    return {
      ngModule: PgkernelgatewaySharedModule
    };
  }
}
