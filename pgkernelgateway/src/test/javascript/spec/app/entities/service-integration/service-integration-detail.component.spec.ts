/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceIntegrationDetailComponent } from 'app/entities/service-integration/service-integration-detail.component';
import { ServiceIntegration } from 'app/shared/model/service-integration.model';

describe('Component Tests', () => {
  describe('ServiceIntegration Management Detail Component', () => {
    let comp: ServiceIntegrationDetailComponent;
    let fixture: ComponentFixture<ServiceIntegrationDetailComponent>;
    const route = ({ data: of({ serviceIntegration: new ServiceIntegration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceIntegrationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceIntegrationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceIntegrationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceIntegration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
