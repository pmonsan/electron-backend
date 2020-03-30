/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ConnectorTypeUpdateComponent } from 'app/entities/connector-type/connector-type-update.component';
import { ConnectorTypeService } from 'app/entities/connector-type/connector-type.service';
import { ConnectorType } from 'app/shared/model/connector-type.model';

describe('Component Tests', () => {
  describe('ConnectorType Management Update Component', () => {
    let comp: ConnectorTypeUpdateComponent;
    let fixture: ComponentFixture<ConnectorTypeUpdateComponent>;
    let service: ConnectorTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ConnectorTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConnectorTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectorTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectorTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConnectorType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConnectorType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
